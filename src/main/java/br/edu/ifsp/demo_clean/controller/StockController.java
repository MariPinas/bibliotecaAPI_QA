package br.edu.ifsp.demo_clean.controller;

import br.edu.ifsp.demo_clean.dto.StockDTO;
import br.edu.ifsp.demo_clean.dto.response.StockResponseDTO;
import br.edu.ifsp.demo_clean.model.Stock;
import br.edu.ifsp.demo_clean.service.StockService;
import br.mapper.StockMapper;
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
    public ResponseEntity<StockResponseDTO> getById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(StockMapper.toDTO(stockService.getById(id)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PostMapping
    public ResponseEntity<StockResponseDTO> addStock(@RequestBody StockDTO stock) {
        try {
            return ResponseEntity.ok(StockMapper.toDTO(stockService.addStock(stock)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PutMapping
    public ResponseEntity<StockResponseDTO> updateStock(@RequestBody Stock stock) {
        try {
            return ResponseEntity.ok(StockMapper.toDTO(stockService.updateStock(stock)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StockResponseDTO> deleteStock(@PathVariable int id) {
        try {
            return ResponseEntity.ok(StockMapper.toDTO(stockService.deleteStock(id)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
