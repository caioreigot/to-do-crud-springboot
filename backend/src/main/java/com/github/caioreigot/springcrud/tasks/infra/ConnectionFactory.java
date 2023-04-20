package com.github.caioreigot.springcrud.tasks.infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

   static String dms = "mysql";
   static String host = "localhost";
   static String port = "3306";
   static String database = "crudspringboot";
   static String user = "root";
   static String password = "";

   public static Connection getConnection() {
      try {
         String url = String.format("jdbc:%s://%s:%s/%s", dms, host, port, database);
         return DriverManager.getConnection(url, user, password);
      } catch (SQLException error) {
         throw new RuntimeException(error.getMessage());
      }
   }
}
