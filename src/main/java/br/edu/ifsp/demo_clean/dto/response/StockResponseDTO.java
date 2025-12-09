package br.edu.ifsp.demo_clean.dto.response;

import br.edu.ifsp.demo_clean.model.Book;

public class StockResponseDTO {
    public int code;
    public boolean availability;
    public Book book;

    public StockResponseDTO() {
    }

    public StockResponseDTO(int code, boolean availability, Book book) {
        this.code = code;
        this.availability = availability;
        this.book = book;
    }
}
