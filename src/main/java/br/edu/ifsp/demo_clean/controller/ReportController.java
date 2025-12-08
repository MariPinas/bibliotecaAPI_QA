package br.edu.ifsp.demo_clean.controller;

import br.edu.ifsp.demo_clean.dto.response.LoanResponseDTO;
import br.edu.ifsp.demo_clean.dto.response.UserResponseDTO;
import br.edu.ifsp.demo_clean.model.Book;
import br.edu.ifsp.demo_clean.model.Stock;
import br.edu.ifsp.demo_clean.service.BookService;
import br.edu.ifsp.demo_clean.service.LoanService;
import br.edu.ifsp.demo_clean.service.StockService;
import br.edu.ifsp.demo_clean.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library/report")
@Tag(name = "Relatórios", description = "Responsável por listar relatórios")
public class ReportController {
    private final BookService bookService;
    private final UserService userService;
    private final StockService stockService;
    private final LoanService loanService;

    public ReportController(BookService bookService, UserService userService, StockService stockService, LoanService loanService) {
        this.bookService = bookService;
        this.userService = userService;
        this.stockService = stockService;
        this.loanService = loanService;
    }

    private <T> ResponseEntity<List<T>> handleReport(List<T> list) {
        try {
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getUsers() {
        return handleReport(userService.getUsers());
    }

    @GetMapping({ "/books" })
    public ResponseEntity<List<Book>> getBooks() {
        return handleReport(bookService.listBooks());
    }

    @GetMapping("/stock")
    public ResponseEntity<List<Stock>> getStock() {
        return handleReport(stockService.getAllAvailable());
    }

    @GetMapping("/loan/assets")
    public ResponseEntity<List<LoanResponseDTO>> getAssets() {
        return handleReport(loanService.listAssets());
    }

    @GetMapping("/loan/history")
    public ResponseEntity<List<LoanResponseDTO>> getHistory() {
        return handleReport(loanService.listHistory());
    }
}
