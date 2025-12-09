package br.edu.ifsp.demo_clean.dto.response;

import br.edu.ifsp.demo_clean.model.enums.BookCategory;

public class BookResponseDTO {
    public int id;
    public int isbn;
    public String title;
    public String author;
    public String publisher;
    public String edition;
    public BookCategory category;

    public BookResponseDTO() {}

    public BookResponseDTO(int id, int isbn, String title, String author, String publisher, String edition, BookCategory category) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.edition = edition;
        this.category = category;
    }
}