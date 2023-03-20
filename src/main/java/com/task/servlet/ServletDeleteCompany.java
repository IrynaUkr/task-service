package com.task.servlet;

import com.task.dbmanger.DBManager;
import com.task.entity.Company;
import jakarta.persistence.EntityManager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ServletDeleteCompany", value = "/ServletDeleteCompany")
public class ServletDeleteCompany extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager entityManager = DBManager.getEntityManager();
        entityManager.getTransaction().begin();
        Integer id = Integer.parseInt(request.getParameter("id"));
        Company company = entityManager.find(Company.class, id);
        entityManager.remove(company);
        entityManager.getTransaction().commit();
    }

}
