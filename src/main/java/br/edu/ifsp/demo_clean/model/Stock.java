package br.edu.ifsp.demo_clean.model;

import jakarta.persistence.*;

@Entity
public class Stock {
    @Id
    private int code;

    @Column(nullable = false)
    private boolean availability;

    @ManyToOne
    public Book book;

    public Stock() {
    }

    public Stock(int code, boolean availability, Book book) {
        this.code = code;
        this.availability = availability;
        this.book = book;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean getAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}