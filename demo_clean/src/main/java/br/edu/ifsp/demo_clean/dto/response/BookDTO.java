package br.edu.ifsp.demo_clean.dto.response;

import br.edu.ifsp.demo_clean.model.enums.CategoriaLivro;

public class BookDTO {
    public final int id;
    public final int isbn;
    public final String title;
    public final String author;
    public final String publisher;
    public final String edition;
    public final CategoriaLivro category;

    public BookDTO(int id, int isbn, String title, String author, String publisher, String edition, CategoriaLivro category) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.edition = edition;
        this.category = category;
    }
}
