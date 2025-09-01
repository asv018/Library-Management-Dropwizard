// File: src/test/java/com/flipfit/dao/AuthorDAOTest.java
package com.librarymanagement.dao;

import com.librarymanagement.models.Author;
import com.librarymanagement.utils.DBConnection;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AuthorDAOTest {

    private AuthorDAO authorDAO;

    @BeforeEach
    void setUp() {
        authorDAO = new AuthorDAO();
        // Setup database with a clean state for each test
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM authors");
            stmt.executeUpdate("ALTER TABLE authors AUTO_INCREMENT = 1");
            stmt.executeUpdate("INSERT INTO authors (name, birthdate, nationality) VALUES ('Stephen King', '1947-09-21', 'American')");
            stmt.executeUpdate("INSERT INTO authors (name, birthdate, nationality) VALUES ('J.K. Rowling', '1965-07-31', 'British')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Test inserting a new author")
    void testInsert() {
        Author newAuthor = new Author();
        newAuthor.setName("George Orwell");
        newAuthor.setBirthdate(new Date(1903 - 1900, 6, 25)); // Year is 1903
        newAuthor.setNationality("British");

        authorDAO.insert(newAuthor);

        assertNotNull(newAuthor.getId());
        Optional<Author> foundAuthor = authorDAO.findById(newAuthor.getId());
        assertTrue(foundAuthor.isPresent());
        assertEquals("George Orwell", foundAuthor.get().getName());
    }

    @Test
    @DisplayName("Test finding an author by their ID")
    void testFindById() {
        Optional<Author> author = authorDAO.findById(1);
        assertTrue(author.isPresent());
        assertEquals("Stephen King", author.get().getName());

        Optional<Author> notFoundAuthor = authorDAO.findById(999);
        assertFalse(notFoundAuthor.isPresent());
    }

    @Test
    @DisplayName("Test finding all authors")
    void testFindAll() {
        List<Author> authors = authorDAO.findAll();
        assertEquals(2, authors.size());
        assertEquals("Stephen King", authors.get(0).getName());
        assertEquals("J.K. Rowling", authors.get(1).getName());
    }

    @Test
    @DisplayName("Test updating an existing author")
    void testUpdate() {
        Optional<Author> authorOpt = authorDAO.findById(1);
        assertTrue(authorOpt.isPresent());
        Author author = authorOpt.get();
        author.setName("Stephen King Updated");
        author.setNationality("American-Canadian");

        int rowsAffected = authorDAO.update(author);

        assertEquals(1, rowsAffected);
        Optional<Author> updatedAuthor = authorDAO.findById(1);
        assertTrue(updatedAuthor.isPresent());
        assertEquals("Stephen King Updated", updatedAuthor.get().getName());
        assertEquals("American-Canadian", updatedAuthor.get().getNationality());
    }

    @Test
    @DisplayName("Test deleting an author by their ID")
    void testDeleteById() {
        int rowsAffected = authorDAO.deleteById(1);
        assertEquals(1, rowsAffected);
        assertFalse(authorDAO.findById(1).isPresent());
    }

    @Test
    @DisplayName("Test searching for authors by name or nationality")
    void testSearch() {
        List<Author> results = authorDAO.search("King");
        assertEquals(1, results.size());
        assertEquals("Stephen King", results.get(0).getName());

        results = authorDAO.search("British");
        assertEquals(1, results.size());
        assertEquals("J.K. Rowling", results.get(0).getName());
    }

    @Test
    @DisplayName("Test paginated author retrieval")
    void testGetPaginatedAuthors() {
        List<Author> page1 = authorDAO.getPaginatedAuthors(1, 0);
        assertEquals(1, page1.size());
        assertEquals("Stephen King", page1.get(0).getName());

        List<Author> page2 = authorDAO.getPaginatedAuthors(1, 1);
        assertEquals(1, page2.size());
        assertEquals("J.K. Rowling", page2.get(0).getName());

        List<Author> emptyPage = authorDAO.getPaginatedAuthors(1, 2);
        assertTrue(emptyPage.isEmpty());
    }
}