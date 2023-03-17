package com.task.servlet;

import com.task.dbmanger.DBManager;
import com.task.entity.Company;
import com.task.entity.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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


        String companyName = companyFromRequest.getCompanyName();
        String personName = personFromRequest.getPersonName();

        Query queryCompName = entityManager.createQuery("from Company where companyName =: name").setParameter("name", companyName);
        Query queryPersonName = entityManager.createQuery("from Person where personName =: name").setParameter("name", personName);
        List<Company> companies = queryCompName.getResultList();
        List<Person> personList = queryPersonName.getResultList();

        //if company present in DB
        if (!companies.isEmpty()) {
            Company company = companies.get(0);
            //if person present in DB
            if (!personList.isEmpty()) {
                Person personPersisted = personList.get(0);
                List<Person> personListPesrsistedComp = company.getPersonList();
                personListPesrsistedComp.add(personPersisted);
                //if person does not present in DB
            } else {
                personList.add(personFromRequest);
                company.setPersonList(personList);
                entityManager.persist(companyFromRequest);
            }
            //if company does not present in DB
        } else {
            if (!personList.isEmpty()) {
                entityManager.persist(companyFromRequest);
                List<Person> people = new ArrayList<>();
                Person personPersisted = personList.get(0);
                people.add(personPersisted);
                companyFromRequest.setPersonList(people);

            } else {
                personList.add(personFromRequest);
                companyFromRequest.setPersonList(personList);
                entityManager.persist(companyFromRequest);
            }
        }

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
        return company;
    }

    static Person getPersonFromRequest(HttpServletRequest request) {
        Person person = new Person();
        String personName = request.getParameter("personName");
        person.setPersonName(personName);
        return person;
    }

}
