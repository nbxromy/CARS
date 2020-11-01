package com.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        // The code below will make a connection to the localhost database and for now only insert a test value into a table.
        String jdbcURL = "jdbc:postgresql://localhost:5432/Test_Project";
        String username = "postgres";
        String password = "asd";
        try{
            
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("ITS WORKING");

            String sql = "INSERT INTO test (surname, name)" + " VALUES ('Hus', 'Saruul')";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            int rows = statement.executeUpdate(sql);
            if(rows>0){
                System.out.println("New names has been inserted");
            }
            connection.close();
        }
        catch(SQLException e){
            System.out.println("Error in connecting postgres");
            e.printStackTrace();

        }
    }

}
