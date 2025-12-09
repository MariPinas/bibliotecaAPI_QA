package br.mapper;

import br.edu.ifsp.demo_clean.dto.response.StockResponseDTO;
import br.edu.ifsp.demo_clean.model.Stock;

public class StockMapper {
    public static StockResponseDTO toDTO(Stock stock) {
    return new StockResponseDTO(
                stock.getCode(),
                stock.getAvailability(),
                stock.getBook()
            );
    }
}
