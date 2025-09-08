package br.edu.ifsp.demo_clean.dto;

import br.edu.ifsp.demo_clean.model.enums.CategoriaLivro;

public class LivroDTO {
    public int isbn;
    public String titulo;
    public String autor;
    public String editora;
    public String edicao;
    public CategoriaLivro categoria;
}