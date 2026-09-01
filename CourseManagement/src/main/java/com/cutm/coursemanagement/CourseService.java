package com.cutm.coursemanagement;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // CREATE
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    // READ ALL
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // READ BY ID
    public Course getCourseById(int id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
    }

    // UPDATE
    public Course updateCourse(Course course) {

        if (!courseRepository.existsById(course.getId())) {
            throw new CourseNotFoundException(course.getId());
        }

        return courseRepository.save(course);
    }

    // DELETE
    public boolean deleteCourse(int id) {

        if (!courseRepository.existsById(id)) {
            return false;
        }

        courseRepository.deleteById(id);
        return true;
    }
}