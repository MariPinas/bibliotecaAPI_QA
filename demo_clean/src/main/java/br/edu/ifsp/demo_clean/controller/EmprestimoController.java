package br.edu.ifsp.demo_clean.controller;

import br.edu.ifsp.demo_clean.dto.CreateEmprestimoDTO;
import br.edu.ifsp.demo_clean.model.Emprestimo;
import br.edu.ifsp.demo_clean.service.EmprestimoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library/emprestimo")
@Tag(name = "Empréstimo", description = "Gestão de Empréstimos")
public class EmprestimoController {
    private EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @PostMapping("/")
    public String salvaEmprestimo(@RequestBody CreateEmprestimoDTO createEmprestimoDTO) {
        return emprestimoService.addEmprestimo(createEmprestimoDTO.codigoExemplar, createEmprestimoDTO.cpf);
    }

    @PutMapping("/{id}/devolucao")
    public String devolucaoEmprestimo(@PathVariable int id) {
        return emprestimoService.devolucaoEmprestimo(id);
    }

    @GetMapping("/ativos")
    public List<Emprestimo> listaEmprestimosAtivos() {
        return emprestimoService.listaAtivos();
    }

    @GetMapping("/historico")
    public List<Emprestimo> listaEmprestimos() {
        return emprestimoService.listaHistorico();
    }
}
