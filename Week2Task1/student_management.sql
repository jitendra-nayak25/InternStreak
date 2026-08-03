CREATE DATABASE student_management;
USE student_management;
CREATE TABLE student (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    age INT CHECK(age >= 18),
    gender ENUM('Male','Female','Other'),
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(15) UNIQUE,
    course VARCHAR(50),
    city VARCHAR(50),
    admission_date DATE DEFAULT (CURRENT_DATE)
);
DESC student;

INSERT INTO student
(first_name,last_name,age,gender,email,phone,course,city)
VALUES
('Jitendra','Nayak',22,'Male','jitendra@gmail.com','9876543210','MCA','Kendrapara');

INSERT INTO student
(first_name,last_name,age,gender,email,phone,course,city)
VALUES
('Rahul','Das',21,'Male','rahul@gmail.com','9876543211','BCA','Bhubaneswar');

INSERT INTO student
(first_name,last_name,age,gender,email,phone,course,city)
VALUES
('Priya','Sahoo',23,'Female','priya@gmail.com','9876543212','MBA','Cuttack');
SELECT * FROM student;

UPDATE student
SET city='Bangalore',
course='MCA'
WHERE student_id=2;
SELECT * FROM student;

DELETE FROM student
WHERE student_id=3;
Select * from student;
SELECT * FROM student
WHERE course='MCA';