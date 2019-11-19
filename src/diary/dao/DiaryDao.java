package diary.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import diary.beans.DiaryInfoBeans;

public class DiaryDao extends Dao {
    public final String getListSQL = "SELECT `diaries`.`id`, `students`.`last_name` AS `student_last_name`, `students`.`first_name` AS `student_first_name`, `courses`.`course_name`, `classes`.`grade`, `classes`.`class_name`, `diaries`.`date`, `diaries`.`regist_time`, `diaries`.`update_time`, `diaries`.`good_comment`, `diaries`.`bad_comment`, `diaries`.`about_comment`, `teachers`.`last_name` AS `teacher_last_name`, `teachers`.`first_name` AS `teacher_first_name`, `diaries`.`teacher_comment` FROM `diaries` INNER JOIN `students` ON `diaries`.`student_id` = `students`.`student_id` INNER JOIN `classes` ON `diaries`.`class_code` = `classes`.`class_code` INNER JOIN `courses` ON `classes`.`course_code` = `courses`.`course_code` LEFT OUTER JOIN `teachers` ON `diaries`.`teacher_id` = `teachers`.`teacher_id` WHERE `diaries`.`class_code` = ?;";
    public final String getMaxIdSQL = "SELECT MAX(`id`) AS `max`, COUNT(`id`) AS `count` FROM `diaries`;";
    public final String insertSQL = "INSERT INTO `diaries`(`id`, `class_code`, `date`, `student_id`, `regist_time`, `update_time`, `good_comment`, `bad_comment`, `about_comment`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

    public List<DiaryInfoBeans> getList(int classId) {
        // If not connected to DB, return null
        if(con == null) {
            System.out.println("データベースに接続していません。");
            return null;
        }

        // SQL Execute
        PreparedStatement stmt = null;
        DiaryInfoBeans diaryInfo = null;
        List<DiaryInfoBeans> diaryList = new ArrayList<DiaryInfoBeans>();
        try {
            stmt = con.prepareStatement(getListSQL);
            stmt.setInt(1, classId);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                diaryInfo = new DiaryInfoBeans();
                diaryInfo.setDiaryId(rs.getInt("id"));
                diaryInfo.setUserName(rs.getString("student_last_name") + " " + rs.getString("student_first_name"));
                diaryInfo.setClassName(rs.getString("course_name") + rs.getString("grade") + rs.getString("class_name"));
                diaryInfo.setDate(rs.getDate("date"));
                diaryInfo.setRegistTime(rs.getTimestamp("regist_time"));
                diaryInfo.setUpdateTime(rs.getTimestamp("update_time"));
                diaryInfo.setGoodComment(rs.getString("good_comment"));
                diaryInfo.setBadComment(rs.getString("bad_comment"));
                diaryInfo.setAboutComment(rs.getString("about_comment"));

                if(rs.getString("teacher_last_name") == null || rs.getString("teacher_first_name") == null) {
                    diaryInfo.setTeacherName(null);
                } else {
                    diaryInfo.setTeacherName(rs.getString("teacher_last_name") + " " + rs.getString("teacher_first_name"));
                }

                diaryInfo.setTeacherComment(rs.getString("teacher_comment"));

                diaryList.add(diaryInfo);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        // If there is no result, return null
        if(diaryList.size() <= 0) {
            return null;
        }

        return diaryList;
    }

    public Integer getMaxId() {
        // If not connected to DB, return null
        if(con == null) {
            System.out.println("データベースに接続していません。");
            return null;
        }

        // Prepare
        Integer maxId = null;

        // SQL Execute
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(getMaxIdSQL);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                // Get Result
                if(rs.getInt("count") > 0) {
                    maxId = rs.getInt("max");
                } else {
                    maxId = 0;
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return maxId;
    }

    public boolean insert(DiaryInfoBeans diaryInfo) {
        // If not connected to DB, return null
        if(con == null) {
            System.out.println("データベースに接続していません。");
            return false;
        }

        // Prepare
        int executeNum = 0;

        // SQL Execute
        PreparedStatement stmt = null;

        try {
            // SQL Prepare
            stmt = con.prepareStatement(insertSQL);

            // Parameter Prepare
            stmt.setInt(1, diaryInfo.getDiaryId());
            stmt.setInt(2, diaryInfo.getClassId());
            stmt.setDate(3, new java.sql.Date(diaryInfo.getDate().getTime()));
            stmt.setString(4, diaryInfo.getUserId());
            stmt.setTimestamp(5, new java.sql.Timestamp(diaryInfo.getRegistTime().getTime()));
            stmt.setTimestamp(6, new java.sql.Timestamp(diaryInfo.getUpdateTime().getTime()));
            stmt.setString(7, diaryInfo.getGoodComment());
            stmt.setString(8, diaryInfo.getBadComment());
            stmt.setString(9, diaryInfo.getAboutComment());

            // Execute
            executeNum = stmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }

        return (executeNum == 1) ? true : false;
    }
}
