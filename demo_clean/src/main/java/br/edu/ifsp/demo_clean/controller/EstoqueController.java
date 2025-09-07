package br.edu.ifsp.demo_clean.controller;

import br.edu.ifsp.demo_clean.dto.EstoqueDTO;
import br.edu.ifsp.demo_clean.model.Estoque;
import br.edu.ifsp.demo_clean.model.Livro;
import br.edu.ifsp.demo_clean.service.EstoqueService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library")
@Tag(
        name = "Estoque",
        description = "Responsável por controlar o estoque"
)
public class EstoqueController {
    private EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @GetMapping({"/estoque"})
    public List<Estoque> getAllDisponiveis() {
        try {
            return estoqueService.getAllDisponiveis();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping({"/estoque/{id}"})
    public Estoque getById(@PathVariable int id) {
        try {
            return estoqueService.getById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/estoque")
    public String addEstoque(@RequestBody EstoqueDTO estoque) {
        try{
            estoqueService.addExemplar(estoque);
            return "Estoque cadastrado com sucesso!";
        } catch (Error ex){
            return ex.getMessage();
        }
    }

    @PutMapping({"/estoque"})
    public String updateEstoque(@RequestBody Estoque estoque) {
        try{
            estoqueService.updateExemplar(estoque);
            return "Estoque atualizado com sucesso";
        } catch (Exception ex){
            return ex.getMessage();
        }
    }

    @DeleteMapping({"/estoque/{id}"})
    public String deletarEstoque(@PathVariable int id) throws  Exception {
        try{
            estoqueService.deletarExemplar(id);
            return "Exemplar deletado com sucesso!";
        } catch (Exception ex){
            return ex.getMessage();
        }
    }
}
