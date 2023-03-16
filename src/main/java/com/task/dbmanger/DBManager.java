package com.task.dbmanger;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


public class DBManager {
    private static DBManager instance;
    public static synchronized DBManager getInstance()  {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public static EntityManager getEntityManager() {
        EntityManagerFactory em = Persistence.createEntityManagerFactory("JPAUser");
        EntityManager entityManager = null;
        if (entityManager == null) {
            try {
                entityManager = em.createEntityManager();
            } catch (Throwable ex) {
                throw new ExceptionInInitializerError(ex);
            }
        }
        return entityManager;
    }
}
