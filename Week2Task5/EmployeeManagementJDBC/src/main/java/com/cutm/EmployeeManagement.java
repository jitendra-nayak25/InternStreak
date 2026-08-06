package com.cutm;

import java.sql.*;
import java.util.Scanner;

public class EmployeeManagement {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        Connection con = DBConnection.getConnection();

        if (con == null) {
            System.out.println("Failed to connect to the database.");
            return;
        }

        while (true) {

            System.out.println("\n===== Employee Management System =====");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Search Employee by ID");
            System.out.println("4. Update Employee");
            System.out.println("5. Delete Employee");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    addEmployee(con);
                    break;

                case 2:
                    viewEmployees(con);
                    break;

                case 3:
                    searchEmployee(con);
                    break;

                case 4:
                    updateEmployee(con);
                    break;

                case 5:
                    deleteEmployee(con);
                    break;

                case 6:
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

    // Add Employee
    public static void addEmployee(Connection con) {

        try {

            sc.nextLine();

            System.out.print("Enter Employee Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Department: ");
            String department = sc.nextLine();

            System.out.print("Enter Salary: ");
            double salary = sc.nextDouble();

            String sql = "INSERT INTO employee(emp_name, department, salary) VALUES(?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, department);
            ps.setDouble(3, salary);

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("Employee Added Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // View Employees
    public static void viewEmployees(Connection con) {

        try {

            String sql = "SELECT * FROM employee";

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);

            System.out.println("\n---------------------------------------------------");
            System.out.printf("%-8s %-20s %-15s %-10s\n",
                    "ID", "Name", "Department", "Salary");
            System.out.println("---------------------------------------------------");

            while (rs.next()) {

                System.out.printf("%-8d %-20s %-15s %-10.2f\n",
                        rs.getInt("emp_id"),
                        rs.getString("emp_name"),
                        rs.getString("department"),
                        rs.getDouble("salary"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Search Employee
    public static void searchEmployee(Connection con) {

        try {

            System.out.print("Enter Employee ID: ");
            int id = sc.nextInt();

            String sql = "SELECT * FROM employee WHERE emp_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                System.out.println("\nEmployee Found");

                System.out.println("ID : " + rs.getInt("emp_id"));
                System.out.println("Name : " + rs.getString("emp_name"));
                System.out.println("Department : " + rs.getString("department"));
                System.out.println("Salary : " + rs.getDouble("salary"));

            } else {

                System.out.println("Employee Not Found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Update Employee
    public static void updateEmployee(Connection con) {

        try {

            System.out.print("Enter Employee ID: ");
            int id = sc.nextInt();

            sc.nextLine();

            System.out.print("Enter New Name: ");
            String name = sc.nextLine();

            System.out.print("Enter New Department: ");
            String department = sc.nextLine();

            System.out.print("Enter New Salary: ");
            double salary = sc.nextDouble();

            String sql = "UPDATE employee SET emp_name=?, department=?, salary=? WHERE emp_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, department);
            ps.setDouble(3, salary);
            ps.setInt(4, id);

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("Employee Updated Successfully!");
            else
                System.out.println("Employee ID Not Found.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Delete Employee
    public static void deleteEmployee(Connection con) {

        try {

            System.out.print("Enter Employee ID to Delete: ");
            int id = sc.nextInt();

            String sql = "DELETE FROM employee WHERE emp_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("Employee Deleted Successfully!");
            else
                System.out.println("Employee ID Not Found.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}