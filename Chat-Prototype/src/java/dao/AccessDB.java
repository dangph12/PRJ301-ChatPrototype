package dao;

import java.sql.*;

public class AccessDB {
    private Connection conn = null;
    private static final String DB_URL = "jdbc:sqlserver://";
    private static final String SERVER_NAME = "localhost";
    private static final String PORT_NUMBER = "1433";
    private static final String DB_NAME = "EMPDB";
    private static final String USER = "sa";
    private static final String PASSWORD = "123";
    
    public AccessDB() {
    }

    private String getConnectionURL() {
        return DB_URL + SERVER_NAME + ":" + PORT_NUMBER + ";databaseName=" + DB_NAME; 
    }

    public Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(getConnectionURL(), USER, PASSWORD);
            if (conn != null) {
                System.out.println("Connection Successful!");
            }
        } catch (ClassNotFoundException | SQLException e) {
            //TODO: handle exception
            System.out.println("Error Trace in getConnection(): " + e.getMessage());
        }
        return conn;
    }

    public void closeQuietly(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            if (resource != null) {
                try {
                    resource.close();
                } catch (Exception e) {
                    //TODO: handle exception
                    e.printStackTrace();
                }
            }
        }
    }
}

