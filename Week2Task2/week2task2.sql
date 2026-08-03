USE student_management;

SELECT * FROM student;

SELECT * FROM student
WHERE course='CSE';

SELECT * FROM student
WHERE age > 20;

SELECT * FROM student
ORDER BY first_name ASC;

SELECT course, COUNT(*) AS Total_Students
FROM student
GROUP BY course;

SELECT MAX(age) AS Highest_Age,
MIN(age) AS Lowest_Age
FROM student;

SELECT * FROM student
WHERE first_name LIKE 'A%';


SELECT course, COUNT(*) AS Total_Students
FROM student
GROUP BY course
HAVING COUNT(*) > 2;