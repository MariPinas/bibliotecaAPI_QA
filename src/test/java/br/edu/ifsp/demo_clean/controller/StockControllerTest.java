package br.edu.ifsp.demo_clean.controller;

import br.edu.ifsp.demo_clean.dto.StockRequestDTO;
import br.edu.ifsp.demo_clean.dto.response.BaseResponseDTO;
import br.edu.ifsp.demo_clean.dto.response.StockResponseDTO;
import br.edu.ifsp.demo_clean.model.Book;
import br.edu.ifsp.demo_clean.model.Stock;
import br.edu.ifsp.demo_clean.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StockControllerTest {

    @Mock
    private StockService stockService;

    @InjectMocks
    private StockController stockController;
    private StockRequestDTO stockRequestDTO;
    private Stock mockStock;
    private static final int MOCK_CODE = 1;
    private static final int MOCK_ISBN = 1;

    @BeforeEach
    void setUp() {
        stockRequestDTO = new StockRequestDTO();
        stockRequestDTO.code = MOCK_CODE;
        stockRequestDTO.availability = true;
        stockRequestDTO.isbn = MOCK_ISBN;

        Book book = new Book();
        book.setIsbn(MOCK_ISBN);

        mockStock = new Stock(
                MOCK_CODE,
                true,
                book);
    }

    @Test
    @DisplayName("POST /library/stock")
    void addStock_shouldReturnOk() {
        when(stockService.addStock(stockRequestDTO)).thenReturn(mockStock);

        ResponseEntity<BaseResponseDTO<StockResponseDTO>> response = stockController.addStock(stockRequestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().data);

        verify(stockService, times(1)).addStock(stockRequestDTO);
    }

    @Test
    @DisplayName("POST /library/stock")
    void addStock_shouldReturnBadRequestOnError() {
        String errorMessage = "Não existe um book com o ISBN informado";
        when(stockService.addStock(stockRequestDTO))
                .thenThrow(new IllegalArgumentException(errorMessage));

        ResponseEntity<BaseResponseDTO<StockResponseDTO>> response = stockController.addStock(stockRequestDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody().message);

        verify(stockService, times(1)).addStock(stockRequestDTO);
    }

    @Test
    @DisplayName("GET /library/stock/{id}")
    void getStockById_shouldReturnOk() {
        when(stockService.getById(MOCK_CODE)).thenReturn(mockStock);

        ResponseEntity<BaseResponseDTO<StockResponseDTO>> response =
                stockController.getById(MOCK_CODE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().data);

        verify(stockService, times(1)).getById(MOCK_CODE);
    }

    @Test
    @DisplayName("GET /library/stock/{id}")
    void getStockById_shouldReturnBadRequestOnError() {
        String errorMessage = "Exemplar não encontrado";
        when(stockService.getById(MOCK_CODE))
                .thenThrow(new RuntimeException(errorMessage));

        ResponseEntity<BaseResponseDTO<StockResponseDTO>> response =
                stockController.getById(MOCK_CODE);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody().message);

        verify(stockService, times(1)).getById(MOCK_CODE);
    }

    @Test
    @DisplayName("PUT /library/stock")
    void updateStock_shouldReturnOk() {
        when(stockService.updateStock(stockRequestDTO)).thenReturn(mockStock);

        ResponseEntity<BaseResponseDTO<StockResponseDTO>> response =
                stockController.updateStock(stockRequestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().data);

        verify(stockService, times(1)).updateStock(stockRequestDTO);
    }

    @Test
    @DisplayName("PUT /library/stock")
    void updateStock_shouldReturnBadRequestOnError() {
        String errorMessage = "Exemplar não encontrado";
        when(stockService.updateStock(stockRequestDTO))
                .thenThrow(new IllegalArgumentException(errorMessage));

        ResponseEntity<BaseResponseDTO<StockResponseDTO>> response =
                stockController.updateStock(stockRequestDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody().message);

        verify(stockService, times(1)).updateStock(stockRequestDTO);
    }

    @Test
    @DisplayName("DELETE /library/stock/{id}")
    void deleteStock_shouldReturnOk() {
        when(stockService.deleteStock(MOCK_CODE)).thenReturn(mockStock);

        ResponseEntity<BaseResponseDTO<StockResponseDTO>> response =
                stockController.deleteStock(MOCK_CODE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().data);

        verify(stockService, times(1)).deleteStock(MOCK_CODE);
    }

    @Test
    @DisplayName("DELETE /library/stock/{id}")
    void deleteStock_shouldReturnBadRequestOnError() {
        String errorMessage = "Erro ao excluir o exemplar";
        when(stockService.deleteStock(MOCK_CODE))
                .thenThrow(new IllegalArgumentException(errorMessage));

        ResponseEntity<BaseResponseDTO<StockResponseDTO>> response =
                stockController.deleteStock(MOCK_CODE);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody().message);

        verify(stockService, times(1)).deleteStock(MOCK_CODE);
    }

}