package diary.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
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

@WebServlet("/diary/delete/confirm")
public class DiaryDeleteConfirmServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Session
        HttpSession session = request.getSession(false);
        UserInfoBeans userInfo = (UserInfoBeans)session.getAttribute("userInfo");

        // Get Form Parameter
        int diaryId = 0;
        try {
            diaryId = Integer.parseInt(request.getParameter("id"));
        } catch(NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // Get Diary
        DiaryModel diaryModel = new DiaryModel();
        DiaryInfoBeans deleteDiaryInfo = diaryModel.getDiary(diaryId);

        // Check
        if(deleteDiaryInfo == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        } else if(UserUtil.isStudent(userInfo) && !deleteDiaryInfo.getUserId().equals(userInfo.getUserId())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        // Set Attribute
        deleteDiaryInfo.setDiaryId(diaryId);
        session.setAttribute("deleteDiaryInfo", deleteDiaryInfo);

        // 生徒・教員分岐
        String jsp;
        if(UserUtil.isTeacher(userInfo)) {
            jsp = "../../WEB-INF/jsp/delete_confirm_teacher.jsp";
        } else {
            jsp = "../../WEB-INF/jsp/delete_confirm_student.jsp";
        }

        // JSP
        RequestDispatcher rd = request.getRequestDispatcher(jsp);
        rd.forward(request, response);
    }
}
