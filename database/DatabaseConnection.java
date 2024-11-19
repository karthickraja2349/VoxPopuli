package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DatabaseConnection {

       private static Connection connection = null;

       private DatabaseConnection() {
      
       }

       public static Connection getConnection() {
               if (connection == null) {
                     try {
                         connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Tenkasi", "main", "zoho");
                         System.out.println("JDBC connected successfully");
                    } 
                    catch (SQLException exception) {
                         System.err.println("Failed to connect JDBC driver: " + exception.getMessage());
                    }
              }
              return connection;
       }

       public static void closeConnection() {
              if (connection != null) {
                   try {
                       connection.close();
                       connection = null;
                   } 
                   catch (SQLException exception) {
                      System.err.println("Failed to close connection");
                   }
            }
       }

    public static PreparedStatement getPreparedStatement(String query) {
    PreparedStatement preparedStatement = null;
    try {
        // Check and establish connection if null
        if (connection == null || connection.isClosed()) {
            getConnection();
        }
        if (connection != null) {
            preparedStatement = connection.prepareStatement(query);
        } else {
            System.out.println("Failed to establish database connection for preparing statement.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return preparedStatement;
}

     
     public static void main(String[]args){
             getConnection();
     }        
}     
