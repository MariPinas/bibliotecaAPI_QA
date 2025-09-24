package br.edu.ifsp.demo_clean.controller;

import br.edu.ifsp.demo_clean.adapter.LoanAdapter;
import br.edu.ifsp.demo_clean.dto.RegisterLoanDTO;
import br.edu.ifsp.demo_clean.dto.Result;
import br.edu.ifsp.demo_clean.dto.response.LoanDTO;
import br.edu.ifsp.demo_clean.model.Loan;
import br.edu.ifsp.demo_clean.service.LoanService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library/emprestimo")
@Tag(name = "Empréstimo", description = "Gestão de Empréstimos")
public class LoanController {
    private final LoanService loanService;
    private final LoanAdapter loanAdapter;

    public LoanController(LoanService loanService, LoanAdapter loanAdapter) {
        this.loanService = loanService;
        this.loanAdapter = loanAdapter;
    }

    @PostMapping("/")
    public Result<LoanDTO> save(@RequestBody RegisterLoanDTO registerLoanDTO) {
        final var result = loanService.register(registerLoanDTO.exemplaryCode, registerLoanDTO.cpf);
        return result.map((loanAdapter::toDTO));
    }

    @PutMapping("/{id}/devolution")
    public Result<LoanDTO> devolution(@PathVariable int id) {
        final var result = loanService.devolution(id);
        return result.map((loanAdapter::toDTO));
    }

    @GetMapping("/assets")
    public Result<List<LoanDTO>> assetsList() {
        return Result.success(loanAdapter.toDTOs(loanService.listAssets()));
    }

    @GetMapping("/history")
    public  Result<List<LoanDTO>> history() {
        return Result.success(loanAdapter.toDTOs(loanService.listHistory()));
    }
}
