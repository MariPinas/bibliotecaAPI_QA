package br.edu.ifsp.demo_clean.controller;

import br.edu.ifsp.demo_clean.dto.response.*;
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
public class ReportController extends BaseController {
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

    @GetMapping("/users")
    public ResponseEntity<BaseResponseDTO<List<UserResponseDTO>>> getUsers() {
        return handleRequest(() -> userService.getUsers().stream().map(UserResponseDTO::new).toList());
    }

    @GetMapping({"/books"})
    public ResponseEntity<BaseResponseDTO<List<BookResponseDTO>>> getBooks() {
        return handleRequest(() -> bookService.listBooks().stream().map(BookResponseDTO::new).toList());
    }

    @GetMapping("/stock")
    public ResponseEntity<BaseResponseDTO<List<StockResponseDTO>>> getStock() {
        return handleRequest(() -> stockService.getAllAvailable().stream().map(StockResponseDTO::new).toList());
    }

    @GetMapping("/loan/assets")
    public ResponseEntity<BaseResponseDTO<List<LoanResponseDTO>>> getAssets() {
        return handleRequest(() -> loanService.listAssets().stream().map(LoanResponseDTO::new).toList());
    }

    @GetMapping("/loan/history")
    public ResponseEntity<BaseResponseDTO<List<LoanResponseDTO>>> getHistory() {
        return handleRequest(() -> loanService.listHistory().stream().map(LoanResponseDTO::new).toList());
    }
}
