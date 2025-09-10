package br.edu.ifsp.demo_clean.dto.response;

public class StockDTO {
    public final String bookTitle;
    public final int isbn;
    public final int copyCode;
    public final boolean available;

    public StockDTO(String bookTitle, int isbn, int copyCode, boolean available) {
        this.bookTitle = bookTitle;
        this.isbn = isbn;
        this.copyCode = copyCode;
        this.available = available;
    }
}
