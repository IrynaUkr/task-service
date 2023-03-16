package com.task.servlet;

import com.task.dbmanger.DBManager;
import com.task.entity.Task;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
@WebServlet(name = "GetAllServlet", value = "/getTasks")
public class GetAllTasksServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Session session = DBManager.getInstance().getFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<Task> tasks = session.createQuery("FROM Task").list();
        System.out.println(tasks);
        transaction.commit();
        session.close();
    }

}
