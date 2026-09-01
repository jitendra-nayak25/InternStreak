CREATE DATABASE student_management;

USE student_management;

CREATE TABLE student(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    age INT,
    course VARCHAR(50)
);
RENAME TABLE student TO students;
SHOW TABLES;