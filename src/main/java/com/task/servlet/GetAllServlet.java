package com.task.servlet;

import com.task.dbmanger.DBManager;
import com.task.entity.Task;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "GetAllServlet", value = "/GetAllServlet")
public class GetAllServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Task> allTasks = getAllTask();
        allTasks.forEach(System.out::println);

    }

    private static List<Task> getAllTask() {
        String selectAll = "SELECT * FROM task ORDER BY task_date DESC";
        List<Task> tasks = new ArrayList<>();
        try (Connection con = DBManager.getInstance().connect();
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(selectAll);
            while (rs.next()) {
                tasks.add(extractTask(rs));
            }
            rs.close();
        } catch (
                SQLException | ClassNotFoundException ex) {

            throw new RuntimeException("tasks were  not found", ex);
        }
        return tasks;
    }

    private static Task extractTask(ResultSet rs) throws SQLException {
        Task task = new Task();
        task.setId(rs.getInt("id"));
        task.setPriority(rs.getInt("priority"));
        task.setDescription(rs.getString("description"));
        return task;
    }

}
