package com.cutm.coursemanagement;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
@Tag(
        name = "Course Management",
        description = "APIs for managing courses"
)
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // CREATE
    @Operation(
            summary = "Add a new course",
            description = "Creates a new course in the database"
    )
    @PostMapping
    public ResponseEntity<Course> addCourse(
            @Valid @RequestBody Course course) {

        Course savedCourse = courseService.saveCourse(course);

        return new ResponseEntity<>(
                savedCourse,
                HttpStatus.CREATED
        );
    }

    // READ ALL
    @Operation(
            summary = "Get all courses",
            description = "Returns a list of all available courses"
    )
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {

        List<Course> courses = courseService.getAllCourses();

        return ResponseEntity.ok(courses);
    }

    // READ BY ID
    @Operation(
            summary = "Get course by ID",
            description = "Returns a course using its ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(
            @PathVariable int id) {

        Course course = courseService.getCourseById(id);

        return ResponseEntity.ok(course);
    }

    // UPDATE
    @Operation(
            summary = "Update a course",
            description = "Updates an existing course using its ID"
    )
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(
            @PathVariable int id,
            @Valid @RequestBody Course course) {

        course.setId(id);

        Course updatedCourse = courseService.updateCourse(course);

        return ResponseEntity.ok(updatedCourse);
    }

    // DELETE
    @Operation(
            summary = "Delete a course",
            description = "Deletes a course using its ID"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(
            @PathVariable int id) {

        boolean deleted = courseService.deleteCourse(id);

        if (!deleted) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Course not found with id: " + id);
        }

        return ResponseEntity.ok("Course deleted successfully");
    }
}