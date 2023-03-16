package com.task.servlet;

import com.task.entity.Task;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

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
        EntityManagerFactory em = Persistence.createEntityManagerFactory("JPAUser");
        EntityManager entityManager = em.createEntityManager();
        entityManager.getTransaction().begin();
        Task task = entityManager.find(Task.class, 3);
        System.out.println(task.getLabels().size());
        List<Task> taskList = entityManager.createQuery("from Task", Task.class).getResultList();
        taskList.forEach(System.out::println);
        entityManager.getTransaction().commit();
    }

}
