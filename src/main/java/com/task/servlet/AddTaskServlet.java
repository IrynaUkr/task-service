package com.task.servlet;

import com.task.dbmanger.DBManager;
import com.task.entity.Task;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "AddTaskServlet", value = "/AddTaskServlet")
public class AddTaskServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Task task = getTaskFromRequest(request);
        Task persistedTask = null;
        try {
            persistedTask = addTask(task);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("task was added " + persistedTask);
    }

    private static Task getTaskFromRequest(HttpServletRequest request) {
        Task task = new Task();
        Integer priority = Integer.parseInt(request.getParameter("priority"));
        String description = request.getParameter("description");
        task.setPriority(priority);
        task.setDescription(description);
        return task;
    }

    private static Task addTask(Task task) throws SQLException, ClassNotFoundException {
        String insertTask = "INSERT INTO public.task(priority, description)VALUES (?, ?);";
        try (Connection con = DBManager.getInstance().connect();
             PreparedStatement pstmt = con.prepareStatement(insertTask, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, task.getPriority());
            pstmt.setString(2, task.getDescription());
            int result = pstmt.executeUpdate();
            if (result > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        task.setId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("task was not created", ex);
        }
        return task;
    }
}
