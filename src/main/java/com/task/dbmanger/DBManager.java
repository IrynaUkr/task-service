package com.task.dbmanger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private final String url = "jdbc:postgresql://localhost/task_service";
    private final String user = "postgres";
    private final String password = "123";

    private static DBManager instance;
    public static synchronized DBManager getInstance()  {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public Connection connect() throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgresSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
