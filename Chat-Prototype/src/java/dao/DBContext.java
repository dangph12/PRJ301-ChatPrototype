package dao;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Manages database connection and provides utility methods for closing resources.
 * 
 * This class uses JDBC to connect to a SQL Server database.
 * 
 * @author namdng09
 */
public class DBContext {
    private Connection conn;
    private static DBContext instance;
    
    private static final String DB_URL = "jdbc:sqlserver://";
    private static final String SERVER_NAME = "localhost";
    private static final String PORT_NUMBER = "1433";
    private static final String DB_NAME = "Chat_Prototype";
    private static final String USER = "sa";
    private static final String PASSWORD = "123";

    // Private constructor to prevent instantiation
    private DBContext() {
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
    }
    
    private String getConnectionURL() {
        return DB_URL + SERVER_NAME + ":" + PORT_NUMBER + ";databaseName=" + DB_NAME; 
    }

    public static DBContext getInstance() throws SQLException {
        if (instance == null) {
            instance = new DBContext();
        } else if (instance.getConnection().isClosed()) {
            instance = new DBContext();
        }
        return instance;
    }
    
    /**
     * Get a connection from the DataSource.
     * 
     * @return Connection object
     * @throws SQLException if a database access error occurs
     */
    public Connection getConnection(){
        return conn;
    }

    /**
     * Closes multiple resources quietly.
     * 
     * @param resources One or more AutoCloseable resources to close.
     */
    public void closeQuietly(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            if (resource != null) {
                try {
                    resource.close();
                } catch (Exception e) {
                    //TODO: handle exception
                    System.out.println("ERROR closing Statement: " + e.getMessage());
                }
            }
        }
    }
    
}

