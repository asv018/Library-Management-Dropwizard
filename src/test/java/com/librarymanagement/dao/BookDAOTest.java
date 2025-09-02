// File: src/test/java/com/flipfit/dao/BookDAOTest.java
package com.librarymanagement.dao;

import com.librarymanagement.models.Book;
import com.librarymanagement.utils.DBConnection;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BookDAOTest {

    private BookDAO bookDAO;

    @BeforeEach
    void setUp() {
        bookDAO = new BookDAO();
        // Setup database with a clean state for each test
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            // Clear both tables to ensure a clean state
            stmt.executeUpdate("DELETE FROM books");
            stmt.executeUpdate("DELETE FROM authors");
            stmt.executeUpdate("ALTER TABLE books AUTO_INCREMENT = 1");
            stmt.executeUpdate("ALTER TABLE authors AUTO_INCREMENT = 1");

            // Insert a test author first to satisfy the foreign key constraint
            stmt.executeUpdate("INSERT INTO authors (name, birthdate, nationality) VALUES ('Stephen King', '1947-09-21', 'American')");

            // Now, insert the test books referencing the new author
            stmt.executeUpdate("INSERT INTO books (title, authorId, publishedDate, isbn) VALUES ('The Stand', 1, '1978-10-01', '978-0-385-19957-0')");
            stmt.executeUpdate("INSERT INTO books (title, authorId, publishedDate, isbn) VALUES ('It', 1, '1986-09-15', '978-0-670-81302-5')");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Test inserting a new book")
    void testInsert() {
        Book newBook = new Book();
        newBook.setTitle("Harry Potter and the Sorcerer's Stone");
        newBook.setAuthorId(1); // Use the existing author ID
        newBook.setPublishedDate(new Date(1997 - 1900, 5, 26));
        newBook.setIsbn("978-0-7475-3274-3");

        bookDAO.insert(newBook);

        assertNotNull(newBook.getId());
        Optional<Book> foundBook = bookDAO.findById(newBook.getId());
        assertTrue(foundBook.isPresent());
        assertEquals("Harry Potter and the Sorcerer's Stone", foundBook.get().getTitle());
    }

    @Test
    @DisplayName("Test finding a book by its ID")
    void testFindById() {
        Optional<Book> book = bookDAO.findById(1);
        assertTrue(book.isPresent());
        assertEquals("The Stand", book.get().getTitle());

        Optional<Book> notFoundBook = bookDAO.findById(999);
        assertFalse(notFoundBook.isPresent());
    }

    @Test
    @DisplayName("Test finding all books")
    void testFindAll() {
        List<Book> books = bookDAO.findAll();
        assertEquals(2, books.size());
        assertEquals("The Stand", books.get(0).getTitle());
        assertEquals("It", books.get(1).getTitle());
    }

    @Test
    @DisplayName("Test updating an existing book")
    void testUpdate() {
        Optional<Book> bookOpt = bookDAO.findById(1);
        assertTrue(bookOpt.isPresent());
        Book book = bookOpt.get();
        book.setTitle("The Stand (Uncut Edition)");
        book.setIsbn("978-0-385-19957-1");

        int rowsAffected = bookDAO.update(book);

        assertEquals(1, rowsAffected);
        Optional<Book> updatedBook = bookDAO.findById(1);
        assertTrue(updatedBook.isPresent());
        assertEquals("The Stand (Uncut Edition)", updatedBook.get().getTitle());
        assertEquals("978-0-385-19957-1", updatedBook.get().getIsbn());
    }

    @Test
    @DisplayName("Test deleting a book by its ID")
    void testDeleteById() {
        int rowsAffected = bookDAO.deleteById(1);
        assertEquals(1, rowsAffected);
        assertFalse(bookDAO.findById(1).isPresent());
    }

    @Test
    @DisplayName("Test searching for books by title or ISBN")
    void testSearch() {
        List<Book> results = bookDAO.search("Stand");
        assertEquals(1, results.size());
        assertEquals("The Stand", results.get(0).getTitle());

        results = bookDAO.search("978-0-670");
        assertEquals(1, results.size());
        assertEquals("It", results.get(0).getTitle());
    }

    @Test
    @DisplayName("Test paginated book retrieval")
    void testGetPaginatedBooks() {
        List<Book> page1 = bookDAO.getPaginatedBooks(1, 0);
        assertEquals(1, page1.size());
        assertEquals("The Stand", page1.get(0).getTitle());

        List<Book> page2 = bookDAO.getPaginatedBooks(1, 1);
        assertEquals(1, page2.size());
        assertEquals("It", page2.get(0).getTitle());

        List<Book> emptyPage = bookDAO.getPaginatedBooks(1, 2);
        assertTrue(emptyPage.isEmpty());
    }
}