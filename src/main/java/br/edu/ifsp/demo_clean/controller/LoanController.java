package br.edu.ifsp.demo_clean.controller;

import br.edu.ifsp.demo_clean.dto.LoanDTO;
import br.edu.ifsp.demo_clean.model.Loan;
import br.edu.ifsp.demo_clean.service.LoanService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library/emprestimo")
@Tag(name = "Empréstimos", description = "Gestão de Empréstimos")
public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/")
    public ResponseEntity<Loan> save(@RequestBody LoanDTO loanDTO) {
        try {
            return ResponseEntity.ok(loanService.register(loanDTO.stockCode, loanDTO.cpf));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PutMapping("/{id}/devolution")
    public ResponseEntity<Loan> devolution(@PathVariable int id) {
        try {
            return ResponseEntity.ok(loanService.devolution(id));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/assets")
    public ResponseEntity<List<Loan>> listAssets() {
        try {
            return ResponseEntity.ok(loanService.listAssets());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/history")
    public ResponseEntity<List<Loan>> history() {
        try {
            return ResponseEntity.ok(loanService.listHistory());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
