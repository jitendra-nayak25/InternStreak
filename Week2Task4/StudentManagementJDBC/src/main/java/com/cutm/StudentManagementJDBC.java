package com.cutm;
import java.sql.*;
import java.util.Scanner;

public class StudentManagementJDBC {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        Connection con = DBConnection.getConnection();

        if (con == null) {
            System.out.println("Failed to connect to the database.");
            return;
        }

        while (true) {

            System.out.println("\n===== Student Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    addStudent(con);
                    break;

                case 2:
                    viewStudents(con);
                    break;

                case 3:
                    updateStudent(con);
                    break;

                case 4:
                    deleteStudent(con);
                    break;

                case 5:
                    try {
                        con.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("Thank You!");
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }

    // Add Student
    public static void addStudent(Connection con) {

        try {

            System.out.print("Enter Name: ");
            sc.nextLine();
            String name = sc.nextLine();

            System.out.print("Enter Age: ");
            int age = sc.nextInt();

            sc.nextLine();

            System.out.print("Enter Course: ");
            String course = sc.nextLine();

            String sql = "INSERT INTO student(name, age, course) VALUES(?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, course);

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("Student Added Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // View Students
    public static void viewStudents(Connection con) {

        try {

            String sql = "SELECT * FROM student";

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);

            System.out.println("\n-------------------------------------------");
            System.out.printf("%-5s %-20s %-5s %-15s\n", "ID", "Name", "Age", "Course");
            System.out.println("-------------------------------------------");

            while (rs.next()) {

                System.out.printf("%-5d %-20s %-5d %-15s\n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("course"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Update Student
    public static void updateStudent(Connection con) {

        try {

            System.out.print("Enter Student ID: ");
            int id = sc.nextInt();

            sc.nextLine();

            System.out.print("Enter New Name: ");
            String name = sc.nextLine();

            System.out.print("Enter New Age: ");
            int age = sc.nextInt();

            sc.nextLine();

            System.out.print("Enter New Course: ");
            String course = sc.nextLine();

            String sql = "UPDATE student SET name=?, age=?, course=? WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, course);
            ps.setInt(4, id);

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("Student Updated Successfully!");
            else
                System.out.println("Student ID Not Found.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Delete Student
    public static void deleteStudent(Connection con) {

        try {

            System.out.print("Enter Student ID to Delete: ");
            int id = sc.nextInt();

            String sql = "DELETE FROM student WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("Student Deleted Successfully!");
            else
                System.out.println("Student ID Not Found.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
