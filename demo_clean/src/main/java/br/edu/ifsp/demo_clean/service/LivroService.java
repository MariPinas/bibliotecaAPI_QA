package br.edu.ifsp.demo_clean.service;

import br.edu.ifsp.demo_clean.model.Livro;
import br.edu.ifsp.demo_clean.repository.LivroRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class LivroService {
    private LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public void verificarIsbnExistente(int isbn){
        if(this.livroRepository.findByIsbn(isbn) != null){
            throw new Error("ISBN já cadastrado");
        }
    }

    public void addLivro(Livro livro) {
        if(this.livroRepository.findById(livro.id).isPresent() || livro.id != 0){
            throw new Error("ERRO: Não informar ID!!!" + livro.id);
        }

        verificarIsbnExistente(livro.isbn);

        this.livroRepository.save(livro);
    }

    public String listarLivros() {
        List<Livro> listaLivros = this.livroRepository.findAll();

        if(!listaLivros.isEmpty()) {
            String lista = "Livros cadastrados:\n";

            for (Livro livro : this.livroRepository.findAll()) {
                lista += "\n" + livro.esconderDetalhes() + "\n";
            }
            return lista;
        }
        else{
            return "Nenhum livro encontrado!";
        }
    }

    public String buscarLivrosISBN(int isbn) {
        Livro livro = this.livroRepository.findByIsbn(isbn);

        return livro != null ?
                livro.obterDetalhes() :
                "Livro não encontrado";
    }

    public Livro attLivro(Livro livroNovo, int isbnBuscado) {
        Livro livroAtual = this.livroRepository.findByIsbn(isbnBuscado);

        if(livroAtual != null){
            if(livroNovo.isbn != livroAtual.isbn){
                verificarIsbnExistente(livroNovo.isbn);
            }

            livroAtual.isbn = livroNovo.isbn;
            livroAtual.titulo = livroNovo.titulo;
            livroAtual.autor = livroNovo.autor;
            livroAtual.edicao = livroNovo.edicao;
            livroAtual.editora = livroNovo.editora;
            return this.livroRepository.save(livroAtual);
        }
        else {
            throw new Error("Livro não encontrado com o isbn: " + isbnBuscado);
        }
    }

    public void deletarLivro(int isbn){
        Livro livro = this.livroRepository.findByIsbn(isbn);
        
        if(livro != null){
            this.livroRepository.delete(livro);
        }
        else {
            throw new Error("Livro não encontrado");
        }
    }
}