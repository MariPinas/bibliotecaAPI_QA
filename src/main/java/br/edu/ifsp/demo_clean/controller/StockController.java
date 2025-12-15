package br.edu.ifsp.demo_clean.controller;

import br.edu.ifsp.demo_clean.dto.StockRequestDTO;
import br.edu.ifsp.demo_clean.dto.response.BaseResponseDTO;
import br.edu.ifsp.demo_clean.dto.response.StockResponseDTO;
import br.edu.ifsp.demo_clean.model.Stock;
import br.edu.ifsp.demo_clean.service.StockService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library/stock")
@Tag(name = "Estoques", description = "Responsável por controlar o estoque")
public class StockController extends BaseController {
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDTO<StockResponseDTO>> getById(@PathVariable int id) {
        return handleRequest(() -> new StockResponseDTO(stockService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<BaseResponseDTO<StockResponseDTO>> addStock(@RequestBody StockRequestDTO stock) {
        return handleRequest(() -> new StockResponseDTO(stockService.addStock(stock)));
    }

    @PutMapping
    public ResponseEntity<BaseResponseDTO<StockResponseDTO>> updateStock(@RequestBody StockRequestDTO stock) {
        return handleRequest(() -> new StockResponseDTO(stockService.updateStock(stock)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseDTO<StockResponseDTO>> deleteStock(@PathVariable int id) {
        return handleRequest(() -> new StockResponseDTO(stockService.deleteStock(id)));
    }
}
