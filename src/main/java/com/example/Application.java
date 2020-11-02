package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {
    static String jdbcURL = "jdbc:postgresql://localhost:5432/Test_Project";
    static String username = "postgres";
    static String password = "asd";

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
        createReviewTable();
        

    }
    // Function below will make a connection to the localhost database

    public static Connection connectDB() {
        
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("CONNECTION");

            return connection;
        } catch (SQLException e) {
            System.out.println("Error in connecting postgres");
            e.printStackTrace();
            return null;
        }
    }

    // Function below will insert data inside the review table.

    public static void insertReviewTable(String usn, String passw, String review) throws Exception{

        try{
            Connection conn = connectDB();
            PreparedStatement insert = conn.prepareStatement("INSERT INTO reviewtablee (usernam, passw, reviewmssg) VALUES ('"+usn+"', '"+ passw+"', '"+review +"')");
            insert.executeUpdate();
            System.out.println("Inserted");
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    // Function below will create a review table inside the database if it doesn't exist.

    public static void createReviewTable() throws Exception {
        try{
            Connection conn = DriverManager.getConnection(jdbcURL, username, password);
            PreparedStatement create = conn.prepareStatement ("CREATE TABLE IF NOT EXISTS ReviewTablee (usernam text NOT NULL, passw text NOT NULL, reviewmssg text NOT NULL, PRIMARY KEY(usernam))");
            create.executeUpdate();
            System.out.print("Review table, executed");
        }
        catch(Exception e){
            System.out.println(e);
        }   
    }

}
