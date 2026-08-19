package com.cutm.coursemanagement;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseRepository {

    private final JdbcTemplate jdbcTemplate;

    public CourseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // INSERT
    public int save(Course course) {

        String sql = "INSERT INTO course (name, duration, fee) VALUES (?, ?, ?)";

        return jdbcTemplate.update(
                sql,
                course.getName(),
                course.getDuration(),
                course.getFee()
        );
    }

    // SELECT ALL
    public List<Course> findAll() {

        String sql = "SELECT * FROM course";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            Course course = new Course();

            course.setId(rs.getInt("id"));
            course.setName(rs.getString("name"));
            course.setDuration(rs.getString("duration"));
            course.setFee(rs.getDouble("fee"));

            return course;
        });
    }

    // SELECT BY ID
    public Course findById(int id) {

        String sql = "SELECT * FROM course WHERE id = ?";

        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> {

                    Course course = new Course();

                    course.setId(rs.getInt("id"));
                    course.setName(rs.getString("name"));
                    course.setDuration(rs.getString("duration"));
                    course.setFee(rs.getDouble("fee"));

                    return course;
                },
                id
        );
    }

    // UPDATE
    public int update(Course course) {

        String sql = "UPDATE course SET name = ?, duration = ?, fee = ? WHERE id = ?";

        return jdbcTemplate.update(
                sql,
                course.getName(),
                course.getDuration(),
                course.getFee(),
                course.getId()
        );
    }

    // DELETE
    public int delete(int id) {

        String sql = "DELETE FROM course WHERE id = ?";

        return jdbcTemplate.update(sql, id);
    }
}