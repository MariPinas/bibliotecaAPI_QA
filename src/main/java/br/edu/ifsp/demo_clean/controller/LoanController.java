package br.edu.ifsp.demo_clean.controller;

import br.edu.ifsp.demo_clean.dto.LoanRequestDTO;
import br.edu.ifsp.demo_clean.dto.response.BaseResponseDTO;
import br.edu.ifsp.demo_clean.dto.response.LoanResponseDTO;
import br.edu.ifsp.demo_clean.service.LoanService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library/loan")
@Tag(name = "Empréstimos", description = "Gestão de Empréstimos")
public class LoanController extends BaseController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/")
    public ResponseEntity<BaseResponseDTO<LoanResponseDTO>> register(@RequestBody LoanRequestDTO loanRequestDTO) {
        return handleRequest(() -> new LoanResponseDTO(loanService.register(loanRequestDTO.stockCode, loanRequestDTO.cpf)));
    }

    @PutMapping("/{id}/devolution")
    public ResponseEntity<BaseResponseDTO<LoanResponseDTO>> devolution(@PathVariable int id) {
        return handleRequest(() -> new LoanResponseDTO(loanService.devolution(id)));
    }
}
