package br.edu.ifsp.demo_clean.dto.response;

import br.edu.ifsp.demo_clean.model.Book;
import br.edu.ifsp.demo_clean.model.enums.BookCategory;

public class BookResponseDTO {
    public int id;
    public int isbn;
    public String title;
    public String author;
    public String publisher;
    public String edition;
    public BookCategory category;

    public BookResponseDTO(Book book) {
        this.id = book.getId();
        this.isbn = book.getIsbn();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.publisher = book.getPublisher();
        this.edition = book.getEdition();
        this.category = book.getCategory();
    }
}