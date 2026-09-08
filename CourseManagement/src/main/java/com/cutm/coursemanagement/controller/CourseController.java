package com.cutm.coursemanagement.controller;

import com.cutm.coursemanagement.Course;
import com.cutm.coursemanagement.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "bearerAuth")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/admin")
    public String adminAccess() {
        return "Welcome Admin! You have ADMIN access.";
    }

    // CREATE
    @Operation(
            summary = "Add a new course",
            description = "Creates a new course in the database"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Course created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid course data"
            )
    })
    @PostMapping
    public ResponseEntity<Course> addCourse(
            @Valid @RequestBody Course course) {

        Course savedCourse = courseService.saveCourse(course);

        return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
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
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Course found"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Course not found"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable int id) {
        Course course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }

    // UPDATE
    @Operation(
            summary = "Update a course",
            description = "Updates an existing course using its ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Course updated successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Course not found"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid course data"
            )
    })
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
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Course deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Course not found"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable int id) {

        boolean deleted = courseService.deleteCourse(id);

        if (!deleted) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Course not found with id: " + id);
        }

        return ResponseEntity.ok("Course deleted successfully");
    }
}