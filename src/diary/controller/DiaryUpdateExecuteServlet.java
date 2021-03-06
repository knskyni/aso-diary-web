package diary.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import diary.beans.DiaryInfoBeans;
import diary.beans.UserInfoBeans;
import diary.model.DiaryModel;
import diary.util.UserUtil;

@WebServlet("/diary/update/execute")
public class DiaryUpdateExecuteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Session
        HttpSession session = request.getSession(false);
        UserInfoBeans userInfo = (UserInfoBeans)session.getAttribute("userInfo");
        DiaryInfoBeans updateDiaryInfo = (DiaryInfoBeans)session.getAttribute("updateDiaryInfo");
        if(updateDiaryInfo == null) {
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
            return;
        }

        // Execute
        DiaryModel diaryModel = new DiaryModel();
        if(UserUtil.isTeacher(userInfo)) {
            if(!diaryModel.teacherUpdate(updateDiaryInfo)) {
                response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
                return;
            }
        } else {
            if(!diaryModel.update(updateDiaryInfo)) {
                response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
                return;
            }
        }

        response.sendRedirect("complete");
    }
}
