package com.cutm;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentManager manager = new StudentManager();
        boolean running = true;

        System.out.println("=== Student Portal Console System ===");

        while (running) {
            System.out.println("\n1. Add Student\n2. Search Student\n3. Edit Student\n4. Delete Student\n5. List All\n6. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine();
                    manager.addStudent(new Student(id, name, email));
                }
                case 2 -> {
                    System.out.print("Enter ID to search: ");
                    String id = scanner.nextLine();
                    Student s = manager.searchStudent(id);
                    System.out.println(s != null ? s : "Student not found.");
                }
                case 3 -> {
                    System.out.print("Enter ID to edit: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter New Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter New Email: ");
                    String email = scanner.nextLine();
                    if (manager.editStudent(id, name, email)) {
                        System.out.println("Student updated successfully!");
                    } else {
                        System.out.println("Student ID not found.");
                    }
                }
                case 4 -> {
                    System.out.print("Enter ID to delete: ");
                    String id = scanner.nextLine();
                    if (manager.deleteStudent(id)) {
                        System.out.println("Student deleted successfully!");
                    } else {
                        System.out.println("Student ID not found.");
                    }
                }
                case 5 -> manager.listAllStudents();
                case 6 -> {
                    running = false;
                    System.out.println("Exiting system. Goodbye!");
                }
                default -> System.out.println("Invalid choice! Try again.");
            }
        }
        scanner.close();
    }
}