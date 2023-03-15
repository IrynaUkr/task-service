package com.task.servlet;

import com.task.dbmanger.DBManager;
import com.task.entity.Label;
import com.task.entity.Task;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "GetAllServlet", value = "/getTasks")
public class GetAllTasksServlet extends HttpServlet {

    public static final String SELECT = "select * from task_label tl, task t, label l where tl.id_label =l.id_label and tl.id_task = t.id;";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Task> allTask = getAllTask();
        System.out.println(allTask);
    }

    private static List<Task> getAllTask() {
        String selectAll = SELECT;
        HashMap<Integer, Task> tasksMap = new HashMap<>();
        HashMap<Integer, Label> labels = new HashMap<>();
        try (Connection con = DBManager.getInstance().connect();
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(selectAll);
            while (rs.next()) {

                Label label;
                int idLabel = rs.getInt("id_label");
                if (labels.containsKey(idLabel)) {
                    label = labels.get(idLabel);
                } else {
                    label = extractLabel(rs);
                    labels.put(idLabel, label);
                }
                int taskId = rs.getInt("id");
                if (tasksMap.containsKey(taskId)) {
                    tasksMap.get(taskId).getLabelList().add(label);
                } else {
                    Task task = extractTask(rs);
                    List<Label> labelList = new ArrayList<>();
                    labelList.add(label);
                    task.setLabelList(labelList);
                    tasksMap.put(taskId, task);
                }
            }
            rs.close();
        } catch (
                SQLException | ClassNotFoundException ex) {
            throw new RuntimeException("tasks were  not found", ex);
        }
        return new ArrayList<>(tasksMap.values());
    }

    private static Task extractTask(ResultSet rs) throws SQLException {
        Task task = new Task();
        task.setId(rs.getInt("id"));
        task.setPriority(rs.getInt("priority"));
        task.setDescription(rs.getString("description"));
        return task;
    }

    private static Label extractLabel(ResultSet rs) throws SQLException {
        Label label = new Label();
        label.setId((rs.getInt("id_label")));
        label.setName(rs.getString("name"));
        return label;
    }

}
