package br.edu.ifsp.demo_clean.controller;

import br.edu.ifsp.demo_clean.dto.StockDTO;
import br.edu.ifsp.demo_clean.model.Stock;
import br.edu.ifsp.demo_clean.service.StockService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library/stock")
@Tag(name = "Estoques", description = "Responsável por controlar o estoque")
public class StockController {
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(stockService.getById(id));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Stock> addStock(@RequestBody StockDTO stock) {
        try {
            return ResponseEntity.ok(stockService.addStock(stock));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PutMapping
    public ResponseEntity<Stock> updateStock(@RequestBody Stock stock) {
        try {
            return ResponseEntity.ok(stockService.updateStock(stock));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Stock> deleteStock(@PathVariable int id) {
        try {
            return ResponseEntity.ok(stockService.deleteStock(id));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
