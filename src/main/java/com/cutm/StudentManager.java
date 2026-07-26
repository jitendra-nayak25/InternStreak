package com.cutm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



public class StudentManager {
    private final List<Student> students = new ArrayList<>();

    // Add Student
    public void addStudent(Student student) {
        students.add(student);
        System.out.println("Student added successfully!");
    }

    // Search Student by ID
    public Student searchStudent(String id) {
        return students.stream()
                .filter(s -> s.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    // Edit Student Details
    public boolean editStudent(String id, String newName, String newEmail) {
        Student student = searchStudent(id);
        if (student != null) {
            student.setName(newName);
            student.setEmail(newEmail);
            return true;
        }
        return false;
    }

    // Delete Student
    public boolean deleteStudent(String id) {
        return students.removeIf(s -> s.getId().equalsIgnoreCase(id));
    }

    // Display All Students
    public void listAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No student records found.");
        } else {
            students.forEach(System.out::println);
        }
    }
}

