package br.edu.ifsp.demo_clean.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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

    public Livro() {
    }

    public Livro(int isbn, String titulo, String autor, String editora, String edicao) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.edicao = edicao;
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