package com.task.servlet;

import com.task.dbmanger.DBManager;
import com.task.entity.Company;
import jakarta.persistence.EntityManager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

import static com.task.servlet.ServletCreate.getCompanyFromRequest;

@WebServlet(name = "ServletUpdateCompany", value = "/ServletUpdateCompany")
public class ServletUpdateCompany extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Company companyFromRequest = getCompanyFromRequest(request);
        Integer id = 0;
        if (companyFromRequest.getCompanyId() != null) {
            id = companyFromRequest.getCompanyId();
            EntityManager entityManager = DBManager.getEntityManager();
            entityManager.getTransaction().begin();
            Company companyPersisted = entityManager.find(Company.class, id);
            companyPersisted.setCompanyName(companyFromRequest.getCompanyName());
            entityManager.getTransaction().commit();
        } else {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("send id");
        }


    }

}
