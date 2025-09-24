package br.edu.ifsp.demo_clean.model;

import br.edu.ifsp.demo_clean.model.enums.CategoriaLivro;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Livro {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    public int id;
    public int isbn;
    public String titulo;
    public String autor;
    public String editora;
    public String edicao;
    public CategoriaLivro categoria;

    @OneToMany(mappedBy = "livro")
    @JsonIgnoreProperties("estoques")
    public List<Estoque> exemplares;

    public Livro() {
    }

    public Livro(int isbn, String titulo, String autor, String editora, String edicao, CategoriaLivro categoria) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.edicao = edicao;
        this.categoria = categoria;
    }

    public String obterDetalhes(){
        return "ID: " + this.id +
                "\nISBN: " + this.isbn +
                "\nTitulo: " + this.titulo +
                "\nAutor: " + this.autor +
                "\nEditora: " + this.editora +
                "\nEdicao: " + this.edicao;
    }

    public String esconderDetalhes(){
        return "ISBN: " + this.isbn +
                "\nTitulo: " + this.titulo;
    }

    public int getId() {
        return id;
    }
}