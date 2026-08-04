CREATE DATABASE library_management;

USE library_management;
CREATE TABLE Authors (
    author_id INT PRIMARY KEY AUTO_INCREMENT,
    author_name VARCHAR(100) NOT NULL,
    country VARCHAR(50)
);

CREATE TABLE Books (
    book_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    author_id INT,
    price DECIMAL(10,2),

    FOREIGN KEY(author_id)
    REFERENCES Authors(author_id)
);

CREATE TABLE Members (
    member_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    join_date DATE
);

CREATE TABLE Book_Issues (
    issue_id INT PRIMARY KEY AUTO_INCREMENT,
    book_id INT,
    member_id INT,
    issue_date DATE,
    return_date DATE,

    FOREIGN KEY(book_id)
    REFERENCES Books(book_id),

    FOREIGN KEY(member_id)
    REFERENCES Members(member_id)
);

INSERT INTO Authors (author_name, country) VALUES
('J.K. Rowling','United Kingdom'),
('Chetan Bhagat','India'),
('Ruskin Bond','India'),
('R.K. Narayan','India'),
('George Orwell','United Kingdom'),
('Paulo Coelho','Brazil'),
('Dan Brown','USA'),
('William Shakespeare','United Kingdom'),
('Jane Austen','United Kingdom'),
('Leo Tolstoy','Russia');

INSERT INTO Books (title, author_id, price) VALUES
('Harry Potter',1,650),
('Five Point Someone',2,350),
('The Blue Umbrella',3,280),
('Malgudi Days',4,400),
('1984',5,500),
('The Alchemist',6,450),
('Inferno',7,700),
('Hamlet',8,320),
('Pride and Prejudice',9,380),
('War and Peace',10,900);

INSERT INTO Members (name, join_date) VALUES
('Amit','2025-01-01'),
('Rahul','2025-01-02'),
('Priya','2025-01-03'),
('Anjali','2025-01-04'),
('Rohit','2025-01-05'),
('Sneha','2025-01-06'),
('Vikas','2025-01-07'),
('Neha','2025-01-08'),
('Arjun','2025-01-09'),
('Kiran','2025-01-10');

INSERT INTO Book_Issues
(book_id, member_id, issue_date, return_date)
VALUES
(1,1,'2025-07-01','2025-07-10'),
(2,2,'2025-07-02','2025-07-11'),
(3,3,'2025-07-03','2025-07-12'),
(4,4,'2025-07-04','2025-07-13'),
(5,5,'2025-07-05','2025-07-14'),
(6,6,'2025-07-06','2025-07-15'),
(7,7,'2025-07-07','2025-07-16'),
(8,8,'2025-07-08','2025-07-17'),
(9,9,'2025-07-09','2025-07-18'),
(10,10,'2025-07-10','2025-07-19');



SELECT
Books.title,
Authors.author_name,
Members.name,
Book_Issues.issue_date,
Book_Issues.return_date

FROM Book_Issues

INNER JOIN Books
ON Book_Issues.book_id=Books.book_id

INNER JOIN Authors
ON Books.author_id=Authors.author_id

INNER JOIN Members
ON Book_Issues.member_id=Members.member_id;

