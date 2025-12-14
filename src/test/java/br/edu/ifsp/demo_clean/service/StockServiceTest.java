package br.edu.ifsp.demo_clean.service;

import br.edu.ifsp.demo_clean.dto.StockRequestDTO;
import br.edu.ifsp.demo_clean.model.Book;
import br.edu.ifsp.demo_clean.model.Stock;
import br.edu.ifsp.demo_clean.repository.StockRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class StockServiceTest {
    @Mock
    public StockRepository stockRepository;

    @Mock
    private BookService bookService;

    @InjectMocks
    private StockService stockService;
    private StockRequestDTO dto;

    @BeforeEach
    void setUp() {
        dto = new StockRequestDTO();
        dto.code = 1;
        dto.availability = true;
        dto.isbn = 123;
    }

    @Test
    @DisplayName("addStock - should save stock when there is book registered")
    void addStock_shouldSaveBook() {
        Book book = mock(Book.class);

        when(bookService.getBookByISBN(dto.isbn)).thenReturn(book);
        when(stockRepository.save(any(Stock.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Stock result = stockService.addStock(dto);

        assertNotNull(result);
        assertEquals(dto.code, result.getCode());
        assertEquals(dto.availability, result.getAvailability());
        assertEquals(book, result.getBook());

        verify(stockRepository).save(any(Stock.class));
    }

    @Test
    @DisplayName("addStock - should throw exception when book does not exist")
    void addStock_shouldThrowWhenBookNotFound() {
        when(bookService.getBookByISBN(dto.isbn)).thenReturn(null);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> stockService.addStock(dto)
        );

        assertEquals(
                "ERRO: Não existe um book com o ISBN informado!",
                ex.getMessage()
        );

        verify(stockRepository, never()).save(any());
    }

    @Test
    @DisplayName("getAllAvailable - should return only available stocks")
    void getAllAvailable_shouldReturnAvailableStocks() {
        List<Stock> stocks = List.of(new Stock(), new Stock());
        when(stockRepository.findByAvailability(true)).thenReturn(stocks);

        List<Stock> result = stockService.getAllAvailable();

        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("getById - should return stock when exists")
    void getById_shouldReturnStock() {
        Stock stock = mock(Stock.class);
        when(stockRepository.findById(1)).thenReturn(Optional.of(stock));

        Stock result = stockService.getById(1);

        assertEquals(stock, result);
    }

    @Test
    @DisplayName("getById - should return null when not found")
    void getById_shouldReturnNullWhenNotFound() {
        when(stockRepository.findById(1)).thenReturn(Optional.empty());

        Stock result = stockService.getById(1);

        assertNull(result);
    }

    @Test
    @DisplayName("updateStock - should update availability when stock exists")
    void updateStock_shouldUpdateStock() {
        Stock existing = new Stock(1, false, mock(Book.class));
        Stock updated = new Stock(1, true, existing.getBook());

        when(stockRepository.findById(1)).thenReturn(Optional.of(existing));
        when(stockRepository.save(any(Stock.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Stock result = stockService.updateStock(updated);

        assertTrue(result.getAvailability());
        verify(stockRepository).save(existing);
    }

    @Test
    @DisplayName("updateStock - should throw when stock does not exist")
    void updateStock_shouldThrowWhenNotFound() {
        Stock stock = new Stock(1, true, mock(Book.class));
        when(stockRepository.findById(1)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> stockService.updateStock(stock)
        );

        assertEquals("Exemplar não encontrado", ex.getMessage());
    }

    @Test
    @DisplayName("deleteStock - should delete when available")
    void deleteStock_shouldDeleteWhenAvailable() {
        Stock stock = mock(Stock.class);
        when(stock.getAvailability()).thenReturn(true);
        when(stockRepository.findById(1)).thenReturn(Optional.of(stock));

        Stock deleted = stockService.deleteStock(1);

        assertEquals(stock, deleted);
        verify(stockRepository).delete(stock);
    }

    @Test
    @DisplayName("deleteStock - should throw when not available")
    void deleteStock_shouldThrowWhenNotAvailable() {
        Stock stock = mock(Stock.class);
        when(stock.getAvailability()).thenReturn(false);
        when(stockRepository.findById(1)).thenReturn(Optional.of(stock));

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> stockService.deleteStock(1)
        );

        assertEquals(
                "Erro ao excluir o exemplar. Ele não está disponível no momento.",
                ex.getMessage()
        );

        verify(stockRepository, never()).delete(any());
    }
}
