// File: src/main/java/com/flipfit/business/AuthorBusiness.java
package com.flipfit.business;

import com.flipfit.bean.Author;
import com.flipfit.dao.AuthorDAO;

import java.util.List;
import java.util.Optional;

public class AuthorBusiness {

    private final AuthorDAO authorDAO;

    public AuthorBusiness(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
    }

    public void createAuthor(Author author) {
        // You can add validation logic here before inserting the author
        authorDAO.insert(author);
    }

    public Optional<Author> getAuthorById(int id) {
        return authorDAO.findById(id);
    }

    public List<Author> getAllAuthors(int page, int size) {
        int offset = page * size;
        return authorDAO.getPaginatedAuthors(size, offset);
    }

    public boolean updateAuthor(Author author) {
        return authorDAO.update(author) > 0;
    }

    public boolean deleteAuthor(int id) {
        return authorDAO.deleteById(id) > 0;
    }

    public List<Author> searchAuthors(String query) {
        String likeQuery = "%" + query + "%";
        return authorDAO.search(likeQuery);
    }
}