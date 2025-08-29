// File: src/main/java/com/flipfit/bean/Author.java
package com.flipfit.bean;

import java.util.Date;

public class Author {
    private int id;
    private String name;
    private Date birthdate;
    private String nationality;

    public Author() {
        // Default constructor
    }

    public Author(int id, String name, Date birthdate, String nationality) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
        this.nationality = nationality;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}