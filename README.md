📚 Library Management System
This project is a simple Library Management System built with Java and JDBC, providing a set of Data Access Objects (DAOs) for managing books and authors.

⚙️ Setup Instructions
To get the project up and running, follow these steps:

1. Prerequisites: Ensure you have the following installed:
   - Java Development Kit (JDK): Version 8 or higher.
   - Apache Maven: For dependency management and building the project.
   - MySQL Database: The application uses a MySQL database.
    
2. Database Setup:
   - Create a database named library_management in your MySQL server.
   - Run the following SQL script to create the necessary tables:
   ```
    CREATE TABLE authors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    birthdate DATE,
    nationality VARCHAR(100)
    );

    CREATE TABLE books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    authorId INT,
    publishedDate DATE,
    isbn VARCHAR(50) UNIQUE,
    FOREIGN KEY (authorId) REFERENCES authors(id)
    );
   ```
3. Environment Variables:
   - The DBConnection.java file is configured to use environment variables for database credentials. This is crucial for security, especially in production environments.
   - Set the following environment variables based on your database configuration:
       - DB_URL 
       - DB_USER
       - DB_PASSWORD
    
4. Build and Run Tests:
   - Open a terminal in the project's root directory.
   - Build the project using Maven:
   ```
   mvn clean install
   ```
   - Run the unit tests to ensure all components are working correctly:
   ```
   mvn test
   ```

📝 API Documentation
The project includes Data Access Objects (DAOs) for Book and Author entities, providing a comprehensive set of CRUD (Create, Read, Update, Delete) and search operations.

BookDAO
```
Method	                 Description
insert(Book book)	       Adds a new book to the database.
findById(int id)	       Retrieves a book by its unique ID.
findAll()	               Fetches a list of all books.
update(Book book)	       Updates an existing book's details.
deleteById(int id)	     Deletes a book by its ID.
search(String query)	   Searches for books by title or ISBN.
getPaginatedBooks(int page, int size)	Retrieves books with pagination.
```

AuthorDAO
```
Method	                 Description
insert(Author author)	   Adds a new author to the database.
findById(int id)	       Retrieves an author by their unique ID.
findAll()	               Fetches a list of all authors.
update(Author author)	   Updates an existing author's details.
deleteById(int id)	     Deletes an author by their ID.
search(String query)	   Searches for authors by name or nationality.
getPaginatedAuthors(int page, int size)	Retrieves authors with pagination.
```

Assumptions
- Database Schema: The provided SQL script is used to set up the database tables and their relationships.
- Database Connectivity: It's assumed that the MySQL server is running and accessible from the machine where the application is being executed.
- Test Data Integrity: The unit tests use the @BeforeEach annotation to clear and re-populate the tables, ensuring each test runs in a clean, predictable state. This practice guarantees test isolation and repeatability.
