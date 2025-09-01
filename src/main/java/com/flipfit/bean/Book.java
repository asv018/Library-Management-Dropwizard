// File: src/main/java/com/flipfit/bean/Book.java
package com.flipfit.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Book {
    private int id;
    private String title;
    private int authorId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date publishedDate;
    private String isbn;

    public Book() {
        // Default constructor
    }

    public Book(int id, String title, int authorId, Date publishedDate, String isbn) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.publishedDate = publishedDate;
        this.isbn = isbn;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}