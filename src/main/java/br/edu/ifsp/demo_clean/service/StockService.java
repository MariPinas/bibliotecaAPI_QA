package br.edu.ifsp.demo_clean.service;

import br.edu.ifsp.demo_clean.dto.StockDTO;
import br.edu.ifsp.demo_clean.model.Stock;
import br.edu.ifsp.demo_clean.model.Book;
import br.edu.ifsp.demo_clean.repository.StockRepository;
import br.edu.ifsp.demo_clean.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {
    public StockRepository stockRepository;
    public BookRepository bookRepository;

    public StockService(StockRepository stockRepository, BookRepository bookRepository) {
        this.stockRepository = stockRepository;
        this.bookRepository = bookRepository;
    }

    public List<Stock> getAllAvailable() {
        return stockRepository.findByAvailability(true);
    }

    public Stock getById(int id) throws Exception {
        try {
            Stock stockLocalizado = null;

            Optional<Stock> estoque = stockRepository.findById(id);

            if (!estoque.isEmpty()) {
                stockLocalizado = estoque.get();
            }

            return stockLocalizado;
        } catch (Exception e) {
            throw new Exception("Não encontrado: ", e);
        }
    }

    public Stock addStock(StockDTO stockDTO) {
        final Book book = bookRepository.findByIsbn(stockDTO.isbn);

        if (book == null) {
            throw new Error("ERRO: Não existe um book com o ISBN informado!");
        }

        Stock stock = new Stock(
                stockDTO.code,
                stockDTO.availability,
                book
        );

        this.stockRepository.save(stock);
        return stock;
    }

    public Stock updateStock(Stock stock) throws Exception {
        //Remover esse try catch
        try {
            Optional<Stock> estoqueUpdate = stockRepository.findById(stock.getCode());

            if (!estoqueUpdate.isEmpty()) {
                Stock stockLocalizado = estoqueUpdate.get();
                stockLocalizado.setAvailability(stock.isAvailability());

                return stockRepository.save(stockLocalizado);
            } else {
                throw new Exception("Exemplar não encontrado");
            }
        } catch (Exception e) {
            throw new Exception("Erro ao atualizar a disponibilidade: ", e);
        }
    }

    public Stock deleteStock(int id) throws Exception {
        //Remover esse try catch
        try {
            final Stock stock = getById(id);

            if (stock.isAvailability()) {
                stockRepository.delete(stock);
                return stock;
            } else {
                throw new Exception("Erro ao excluir o exemplar. Ele não está disponível no momento.");
            }
        } catch (Exception ex) {
            throw new Exception("Exemplar não encontrado");
        }
    }
}

