// File: src/main/java/com/flipfit/dao/AuthorDAO.java
package com.flipfit.dao;

import com.flipfit.bean.Author;
import com.flipfit.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorDAO {

    public void insert(Author author) {
        String sql = "INSERT INTO authors (name, birthdate, nationality) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, author.getName());
            pstmt.setDate(2, new Date(author.getBirthdate().getTime()));
            pstmt.setString(3, author.getNationality());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    author.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Author> findById(int id) {
        String sql = "SELECT * FROM authors WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Author author = new Author();
                    author.setId(rs.getInt("id"));
                    author.setName(rs.getString("name"));
                    author.setBirthdate(rs.getDate("birthdate"));
                    author.setNationality(rs.getString("nationality"));
                    return Optional.of(author);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Author> findAll() {
        List<Author> authors = new ArrayList<>();
        String sql = "SELECT * FROM authors";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Author author = new Author();
                author.setId(rs.getInt("id"));
                author.setName(rs.getString("name"));
                author.setBirthdate(rs.getDate("birthdate"));
                author.setNationality(rs.getString("nationality"));
                authors.add(author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }

    public int update(Author author) {
        String sql = "UPDATE authors SET name = ?, birthdate = ?, nationality = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, author.getName());
            pstmt.setDate(2, new Date(author.getBirthdate().getTime()));
            pstmt.setString(3, author.getNationality());
            pstmt.setInt(4, author.getId());
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int deleteById(int id) {
        String sql = "DELETE FROM authors WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Author> search(String query) {
        List<Author> authors = new ArrayList<>();
        String sql = "SELECT * FROM authors WHERE name LIKE ? OR nationality LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String likeQuery = "%" + query + "%";
            pstmt.setString(1, likeQuery);
            pstmt.setString(2, likeQuery);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Author author = new Author();
                    author.setId(rs.getInt("id"));
                    author.setName(rs.getString("name"));
                    author.setBirthdate(rs.getDate("birthdate"));
                    author.setNationality(rs.getString("nationality"));
                    authors.add(author);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }

    public List<Author> getPaginatedAuthors(int limit, int offset) {
        List<Author> authors = new ArrayList<>();
        String sql = "SELECT * FROM authors LIMIT ? OFFSET ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, limit);
            pstmt.setInt(2, offset);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Author author = new Author();
                    author.setId(rs.getInt("id"));
                    author.setName(rs.getString("name"));
                    author.setBirthdate(rs.getDate("birthdate"));
                    author.setNationality(rs.getString("nationality"));
                    authors.add(author);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }
}