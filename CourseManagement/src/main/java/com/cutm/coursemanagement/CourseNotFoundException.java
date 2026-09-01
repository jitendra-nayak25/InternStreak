package com.cutm.coursemanagement;

public class CourseNotFoundException extends RuntimeException {

    public CourseNotFoundException(int id) {
        super("Course not found with id: " + id);
    }
}