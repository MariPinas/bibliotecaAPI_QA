package br.edu.ifsp.demo_clean.dto;

import br.edu.ifsp.demo_clean.model.enums.BookCategory;

public class BookDTO {
    public int isbn;
    public String title;
    public String author;
    public String publisher;
    public String edition;
    public BookCategory category;
}