package br.edu.ifsp.demo_clean.dto.response;

import br.edu.ifsp.demo_clean.model.Stock;

public class StockResponseDTO {
    public int code;
    public boolean availability;
    public BookResponseDTO book;


    public StockResponseDTO(Stock stock) {
        this.code = stock.getCode();
        this.availability = stock.getAvailability();
        this.book = new BookResponseDTO(stock.getBook());
    }
}
