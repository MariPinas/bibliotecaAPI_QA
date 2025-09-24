package br.edu.ifsp.demo_clean.service;

import br.edu.ifsp.demo_clean.dto.LivroDTO;
import br.edu.ifsp.demo_clean.model.Livro;
import br.edu.ifsp.demo_clean.repository.LivroRepository;

import java.text.Normalizer;
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

    public void verificarAutorEditoraEdicao(String autor, String editora, String edicao){
        if(this.livroRepository.findByAutorAndEditoraAndEdicao(autor, editora, edicao) != null){
            throw new Error("ERRO: Dados de livro já cadastrados!!!");
        }
    }

    public Livro addLivro(LivroDTO livroDTO) {

        verificarIsbnExistente(livroDTO.isbn);
        verificarAutorEditoraEdicao(
                livroDTO.autor,
                livroDTO.editora,
                livroDTO.edicao
        );

        Livro novoLivro = new Livro(
                livroDTO.isbn,
                livroDTO.titulo,
                livroDTO.autor,
                livroDTO.editora,
                livroDTO.edicao,
                livroDTO.categoria
        );

        return this.livroRepository.save(novoLivro);
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

    public Livro attLivro(LivroDTO livroDTO, int isbnBuscado) {
        Livro livroAtual = this.livroRepository.findByIsbn(isbnBuscado);

        if(livroAtual != null){
            if(livroDTO.isbn != livroAtual.isbn){
                verificarIsbnExistente(livroDTO.isbn);
            }

            verificarAutorEditoraEdicao(
                    livroDTO.autor,
                    livroDTO.editora,
                    livroDTO.edicao
            );

            livroAtual.isbn = livroDTO.isbn;
            livroAtual.titulo = livroDTO.titulo;
            livroAtual.autor = livroDTO.autor;
            livroAtual.edicao = livroDTO.edicao;
            livroAtual.editora = livroDTO.editora;
            livroAtual.categoria = livroDTO.categoria;

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