package com.cutm;
import java.sql.Connection;
import java.sql.DriverManager;
public class DBConnection {
    static final String URL = "jdbc:mysql://localhost:3306/student_management";
    static final String USER = "root";
    static final String PASSWORD = "Jk@9337287948";

    public static Connection getConnection() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("Database Connected Successfully!");

            return con;

        } catch(Exception e) {

            e.printStackTrace();

            return null;
        }
    }
}
