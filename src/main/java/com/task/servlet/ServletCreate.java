package com.task.servlet;

import com.task.dbmanger.DBManager;
import com.task.entity.Company;
import com.task.entity.Person;
import jakarta.persistence.EntityManager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ServletCreateCompany", value = "/ServletCreateCompany")
public class ServletCreate extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //validate req
        Company companyFromRequest = getCompanyFromRequest(request);
        Person personFromRequest = getPersonFromRequest(request);

        EntityManager entityManager = DBManager.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(companyFromRequest);
        entityManager.persist(personFromRequest);
        entityManager.getTransaction().commit();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("persisted ");
    }

    static Company getCompanyFromRequest(HttpServletRequest request) {
        Company company = new Company();
        String companyName = request.getParameter("companyName");
        if (companyName != null) {
            company.setCompanyName(companyName);
        }
        Integer id = Integer.parseInt(request.getParameter("id"));
        if(id!=null){
            company.setCompanyId(id);
        }
        return company;
    }

    static Person getPersonFromRequest(HttpServletRequest request) {
        Person person = new Person();
        String personName = request.getParameter("personName");
        person.setPersonName(personName);
        return person;
    }

}
