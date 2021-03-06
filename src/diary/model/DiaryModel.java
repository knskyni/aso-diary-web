package diary.model;

import java.util.Date;
import java.util.List;

import diary.beans.DiaryInfoBeans;
import diary.dao.DiaryDao;

public class DiaryModel {
    public List<DiaryInfoBeans> getList() {
        DiaryDao diaryDao = new DiaryDao();
        List<DiaryInfoBeans> diaryList = null;

        try {
            diaryDao.connect();
            diaryList = diaryDao.getList();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            diaryDao.close();
        }

        return diaryList;
    }

    public List<DiaryInfoBeans> getList(int classId) {
        DiaryDao diaryDao = new DiaryDao();
        List<DiaryInfoBeans> diaryList = null;

        try {
            diaryDao.connect();
            diaryList = diaryDao.getList(classId);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            diaryDao.close();
        }

        return diaryList;
    }

    public boolean insert(DiaryInfoBeans diaryInfo) {
        DiaryDao diaryDao = new DiaryDao();
        boolean result = false;

        try {
            diaryDao.connect();
            // Check for duplicate
            if(diaryDao.insertCheck(diaryInfo)) {
                diaryInfo.setRegistTime(new Date());
                result = diaryDao.insert(diaryInfo);
            }
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            diaryDao.close();
        }

        return result;
    }

    public DiaryInfoBeans getDiary(int diaryId) {
        DiaryDao diaryDao = new DiaryDao();
        DiaryInfoBeans diaryInfo = null;

        try {
            diaryDao.connect();
            diaryInfo = diaryDao.getDiary(diaryId);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            diaryDao.close();
        }

        return diaryInfo;
    }

    public boolean update(DiaryInfoBeans diaryInfo) {
        DiaryDao diaryDao = new DiaryDao();
        boolean result = false;

        try {
            diaryDao.connect();
            diaryInfo.setUpdateTime(new Date());
            result = diaryDao.update(diaryInfo);
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            diaryDao.close();
        }

        return result;
    }

    public boolean delete(DiaryInfoBeans diaryInfo) {
        DiaryDao diaryDao = new DiaryDao();
        boolean result = false;

        try {
            diaryDao.connect();
            result = diaryDao.delete(diaryInfo);
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            diaryDao.close();
        }

        return result;
    }

    public boolean teacherUpdate(DiaryInfoBeans diaryInfo) {
        DiaryDao diaryDao = new DiaryDao();
        boolean result = false;

        try {
            diaryDao.connect();
            result = diaryDao.teacherUpdate(diaryInfo);
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            diaryDao.close();
        }

        return result;
    }

    public boolean teacherDelete(DiaryInfoBeans diaryInfo) {
        DiaryDao diaryDao = new DiaryDao();
        boolean result = false;

        try {
            diaryDao.connect();
            result = diaryDao.teacherDelete(diaryInfo);
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            diaryDao.close();
        }

        return result;
    }
}
