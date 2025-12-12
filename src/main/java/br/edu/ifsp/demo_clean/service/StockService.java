package br.edu.ifsp.demo_clean.service;

import br.edu.ifsp.demo_clean.dto.StockRequestDTO;
import br.edu.ifsp.demo_clean.model.Stock;
import br.edu.ifsp.demo_clean.model.Book;
import br.edu.ifsp.demo_clean.repository.StockRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {
    public StockRepository stockRepository;
    public BookService bookService;

    public StockService(StockRepository stockRepository, BookService bookService) {
        this.stockRepository = stockRepository;
        this.bookService = bookService;
    }

    public List<Stock> getAllAvailable() {
        return stockRepository.findByAvailability(true);
    }

    public Stock getById(int id) {
        Stock stockLocalizado = null;

        Optional<Stock> estoque = stockRepository.findById(id);

        if (!estoque.isEmpty()) {
            stockLocalizado = estoque.get();
        }

        return stockLocalizado;
    }

    public Stock addStock(StockRequestDTO stockRequestDTO) {
        final Book book = bookService.getBookByISBN(stockRequestDTO.isbn);

        if (book == null) {
            throw new IllegalArgumentException("ERRO: Não existe um book com o ISBN informado!");
        }

        Stock stock = new Stock(
                stockRequestDTO.code,
                stockRequestDTO.availability,
                book);

        this.stockRepository.save(stock);
        return stock;
    }

    public Stock updateStock(Stock stock) {
        Optional<Stock> estoqueUpdate = stockRepository.findById(stock.getCode());

        if (!estoqueUpdate.isEmpty()) {
            Stock stockLocalizado = estoqueUpdate.get();
            stockLocalizado.setAvailability(stock.getAvailability());

            return stockRepository.save(stockLocalizado);
        } else {
            throw new IllegalArgumentException("Exemplar não encontrado");
        }
    }

    public Stock deleteStock(int id) {
        final Stock stock = getById(id);

        if (stock.getAvailability()) {
            stockRepository.delete(stock);
            return stock;
        } else {
            throw new IllegalArgumentException("Erro ao excluir o exemplar. Ele não está disponível no momento.");
        }
    }
}
