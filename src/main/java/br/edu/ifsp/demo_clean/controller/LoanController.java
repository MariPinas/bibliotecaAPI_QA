package br.edu.ifsp.demo_clean.controller;

import br.edu.ifsp.demo_clean.dto.LoanDTO;
import br.edu.ifsp.demo_clean.dto.response.LoanResponseDTO;
import br.edu.ifsp.demo_clean.service.LoanService;
import br.mapper.LoanMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library/emprestimo")
@Tag(name = "Empréstimos", description = "Gestão de Empréstimos")
public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/")
    public ResponseEntity<LoanResponseDTO> save(@RequestBody LoanDTO loanDTO) {
        try {
            return ResponseEntity.ok(LoanMapper.toDTO(loanService.register(loanDTO.stockCode, loanDTO.cpf)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PutMapping("/{id}/devolution")
    public ResponseEntity<LoanResponseDTO> devolution(@PathVariable int id) {
        try {
            return ResponseEntity.ok(LoanMapper.toDTO(loanService.devolution(id)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
