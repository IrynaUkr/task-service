package com.task.dbmanger;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DBManager {
    private static SessionFactory sessionFactory = null;
    private static DBManager instance;
    public static synchronized DBManager getInstance()  {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public static SessionFactory getFactory() {
        if (sessionFactory == null) {
            try {
                sessionFactory = new Configuration().configure().buildSessionFactory();
            } catch (Throwable ex) {
                throw new ExceptionInInitializerError(ex);
            }
        }
        return sessionFactory;
    }
}
