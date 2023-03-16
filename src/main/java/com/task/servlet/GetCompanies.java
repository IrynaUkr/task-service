package com.task.servlet;

import com.task.dbmanger.DBManager;
import com.task.entity.Company;
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

@WebServlet(name = "GetCompanies", value = "/getCompanies")
public class GetCompanies extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager entityManager =DBManager.getEntityManager();

        entityManager.getTransaction().begin();
        List<Company> companies = entityManager.createQuery("from Company", Company.class).getResultList();
        companies.forEach(x-> System.out.println(x.getCompanyName()+ " " + x.getPersonList()));


        entityManager.getTransaction().commit();
    }

}
