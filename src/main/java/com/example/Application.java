package com.example;

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
    }
 

}
