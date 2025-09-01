// File: src/main/java/com/flipfit/business/BookBusiness.java
package com.librarymanagement.business;

import com.librarymanagement.models.Book;
//import com.dropwizard.flipfit.dao.BookDAO;
import com.librarymanagement.dao.BookDAO;

import java.util.List;
import java.util.Optional;

public class BookBusiness {

    private final BookDAO bookDAO;

    public BookBusiness(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    public void createBook(Book book) {
        // You can add validation logic here before inserting the book
        bookDAO.insert(book);
    }

    public Optional<Book> getBookById(int id) {
        return bookDAO.findById(id);
    }

    public List<Book> getAllBooks(int page, int size) {
        int offset = page * size;
        return bookDAO.getPaginatedBooks(size, offset);
    }

    public boolean updateBook(Book book) {
        return bookDAO.update(book) > 0;
    }

    public boolean deleteBook(int id) {
        return bookDAO.deleteById(id) > 0;
    }

    public List<Book> searchBooks(String query) {
        String likeQuery = "%" + query + "%";
        return bookDAO.search(likeQuery);
    }
}