package com.cutm.coursemanagement;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public String addCourse(@RequestBody Course course) {
        int result = courseService.saveCourse(course);

        if (result > 0) {
            return "Course added successfully";
        }

        return "Course not added";
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable int id) {
        return courseService.getCourseById(id);
    }

    @PutMapping("/{id}")
    public String updateCourse(@PathVariable int id,
                               @RequestBody Course course) {

        course.setId(id);

        int result = courseService.updateCourse(course);

        if (result > 0) {
            return "Course updated successfully";
        }

        return "Course not found";
    }

    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable int id) {
        int result = courseService.deleteCourse(id);

        if (result > 0) {
            return "Course deleted successfully";
        }

        return "Course not found";
    }
}