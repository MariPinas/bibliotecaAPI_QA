package br.edu.ifsp.demo_clean.controller;

import br.edu.ifsp.demo_clean.dto.LivroDTO;
import br.edu.ifsp.demo_clean.model.Livro;
import br.edu.ifsp.demo_clean.service.LivroService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/library"})
@Tag(
        name = "Livro",
        description = "Responsável por controlar os livros"
)
public class LivroController {
    private LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @PostMapping({"/livro"})
    public String addLivro(@RequestBody LivroDTO livroDTO) {
        try{
            Livro novoLivro = this.livroService.addLivro(livroDTO);
            return "Livro Cadastrado: \n" + novoLivro.obterDetalhes();
        } catch (Error ex){
            return ex.getMessage();
        }

    }

    @GetMapping({"/livro"})
    public String listarLivros() {
        return this.livroService.listarLivros();
    }

    @GetMapping({"/livro/{isbn}"})
    public String buscarLivros(@PathVariable int isbn) {
        return this.livroService.buscarLivrosISBN(isbn);
    }

    @PutMapping({"/livro/{isbn}"})
    public String attLivro(@RequestBody LivroDTO livroDTO, int isbn) {
        try{
            Livro novoLivro = this.livroService.attLivro(livroDTO, isbn);
            return novoLivro.obterDetalhes();
        } catch (Error ex){
            return ex.getMessage();
        }
    }

    @DeleteMapping({"/livro/{isbn}"})
    public String deletarLivro(@PathVariable int isbn) {
        try{
            this.livroService.deletarLivro(isbn);
            return "Livro Deletado com sucesso!!!";
        } catch (Error ex){
            return ex.getMessage();
        }
    }
}