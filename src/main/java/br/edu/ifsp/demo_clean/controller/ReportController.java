package br.edu.ifsp.demo_clean.controller;

import br.edu.ifsp.demo_clean.dto.response.BookResponseDTO;
import br.edu.ifsp.demo_clean.dto.response.LoanResponseDTO;
import br.edu.ifsp.demo_clean.dto.response.StockResponseDTO;
import br.edu.ifsp.demo_clean.dto.response.UserResponseDTO;
import br.edu.ifsp.demo_clean.service.BookService;
import br.edu.ifsp.demo_clean.service.LoanService;
import br.edu.ifsp.demo_clean.service.StockService;
import br.edu.ifsp.demo_clean.service.UserService;
import br.mapper.BookMapper;
import br.mapper.LoanMapper;
import br.mapper.StockMapper;
import br.mapper.UserMapper;
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

    public ReportController(BookService bookService, UserService userService, StockService stockService,
            LoanService loanService) {
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
        return handleReport(
                userService.getUsers()
                        .stream()
                        .map(UserMapper::toDTO)
                        .toList());

    }

    @GetMapping({ "/books" })
    public ResponseEntity<List<BookResponseDTO>> getBooks() {
        return handleReport(
                bookService.listBooks()
                        .stream()
                        .map(BookMapper::toDTO)
                        .toList());
    }

    @GetMapping("/stock")
    public ResponseEntity<List<StockResponseDTO>> getStock() {
        return handleReport(
                stockService.getAllAvailable()
                        .stream()
                        .map(StockMapper::toDTO)
                        .toList());
    }

    @GetMapping("/loan/assets")
    public ResponseEntity<List<LoanResponseDTO>> getAssets() {
        return handleReport(
                loanService.listAssets()
                        .stream()
                        .map(LoanMapper::toDTO)
                        .toList());
    }

    @GetMapping("/loan/history")
    public ResponseEntity<List<LoanResponseDTO>> getHistory() {
        return handleReport(
                loanService.listHistory()
                        .stream()
                        .map(LoanMapper::toDTO)
                        .toList());
    }
}
