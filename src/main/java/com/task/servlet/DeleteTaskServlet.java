package com.task.servlet;

import com.task.dbmanger.DBManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "DeleteTaskServlet", value = "/DeleteTaskServlet")
public class DeleteTaskServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, RuntimeException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        try {
            delTask(id);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void delTask(Integer id) throws SQLException, ClassNotFoundException {
        String delTask = "DELETE FROM public.task WHERE id = ? ;";
        try (Connection con = DBManager.getInstance().connect();
             PreparedStatement pstmt = con.prepareStatement(delTask)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("task was not deleted", ex);
        }
    }
}
