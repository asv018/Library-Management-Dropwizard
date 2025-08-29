// -- Library Management System Database Setup Script
// -- This script creates the database and all necessary tables

// -- Step 1: Create Database
// DROP DATABASE IF EXISTS library_management;
// CREATE DATABASE library_management;
// USE library_management;

// -- Step 2: Create Authors Table
// CREATE TABLE authors (
//     id INT PRIMARY KEY AUTO_INCREMENT,
//     name VARCHAR(255) NOT NULL,
//     birthdate DATE,
//     nationality VARCHAR(100),
//     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
//     INDEX idx_name (name),
//     INDEX idx_nationality (nationality)
// );

// -- Step 3: Create Books Table
// CREATE TABLE books (
//     id INT PRIMARY KEY AUTO_INCREMENT,
//     title VARCHAR(500) NOT NULL,
//     authorId INT NOT NULL,
//     publishedDate DATE,
//     isbn VARCHAR(20) UNIQUE,
//     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
//     FOREIGN KEY (authorId) REFERENCES authors(id) ON DELETE CASCADE,
//     INDEX idx_title (title),
//     INDEX idx_isbn (isbn),
//     INDEX idx_authorId (authorId)
// );

// -- Step 4: Insert Sample Data for Testing

// -- Insert sample authors
// INSERT INTO authors (name, birthdate, nationality) VALUES
// ('J.K. Rowling', '1965-07-31', 'British'),
// ('George R.R. Martin', '1948-09-20', 'American'),
// ('Haruki Murakami', '1949-01-12', 'Japanese'),
// ('Gabriel García Márquez', '1927-03-06', 'Colombian'),
// ('Agatha Christie', '1890-09-15', 'British'),
// ('Stephen King', '1947-09-21', 'American'),
// ('Paulo Coelho', '1947-08-24', 'Brazilian'),
// ('Dan Brown', '1964-06-22', 'American'),
// ('Margaret Atwood', '1939-11-18', 'Canadian'),
// ('Chimamanda Ngozi Adichie', '1977-09-15', 'Nigerian');

// -- Insert sample books
// INSERT INTO books (title, authorId, publishedDate, isbn) VALUES
// ('Harry Potter and the Philosopher\'s Stone', 1, '1997-06-26', '978-0-7475-3269-9'),
// ('Harry Potter and the Chamber of Secrets', 1, '1998-07-02', '978-0-7475-3849-3'),
// ('Harry Potter and the Prisoner of Azkaban', 1, '1999-07-08', '978-0-7475-4215-5'),
// ('A Game of Thrones', 2, '1996-08-06', '978-0-553-10354-0'),
// ('A Clash of Kings', 2, '1998-11-16', '978-0-553-10803-3'),
// ('Norwegian Wood', 3, '1987-09-04', '978-4-06-203581-2'),
// ('Kafka on the Shore', 3, '2002-09-12', '978-1-4000-7927-8'),
// ('One Hundred Years of Solitude', 4, '1967-05-30', '978-0-06-088328-7'),
// ('Love in the Time of Cholera', 4, '1985-09-05', '978-0-307-38973-2'),
// ('Murder on the Orient Express', 5, '1934-01-01', '978-0-06-269366-2'),
// ('And Then There Were None', 5, '1939-11-06', '978-0-312-33087-3'),
// ('The Shining', 6, '1977-01-28', '978-0-385-12167-5'),
// ('It', 6, '1986-09-15', '978-0-670-81302-0'),
// ('The Alchemist', 7, '1988-05-01', '978-0-06-112241-5'),
// ('The Da Vinci Code', 8, '2003-03-18', '978-0-385-50420-5'),
// ('Angels & Demons', 8, '2000-05-01', '978-0-671-02735-3'),
// ('The Handmaid\'s Tale', 9, '1985-09-01', '978-0-385-49081-8'),
// ('Americanah', 10, '2013-05-14', '978-0-307-45592-5'),
// ('Half of a Yellow Sun', 10, '2006-09-12', '978-1-4000-4776-0');

// -- Step 5: Create Views for Common Queries (Optional)

// -- View for books with author details
// CREATE VIEW book_author_view AS
// SELECT 
//     b.id as book_id,
//     b.title,
//     b.isbn,
//     b.publishedDate,
//     a.id as author_id,
//     a.name as author_name,
//     a.nationality as author_nationality
// FROM books b
// JOIN authors a ON b.authorId = a.id;

// -- View for author book counts
// CREATE VIEW author_book_count AS
// SELECT 
//     a.id,
//     a.name,
//     a.nationality,
//     COUNT(b.id) as book_count
// FROM authors a
// LEFT JOIN books b ON a.id = b.authorId
// GROUP BY a.id, a.name, a.nationality;

// -- Step 6: Verify the setup
// SELECT 'Database setup completed successfully!' as message;

// -- Display some statistics
// SELECT 
//     (SELECT COUNT(*) FROM authors) as total_authors,
//     (SELECT COUNT(*) FROM books) as total_books;