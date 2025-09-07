package br.edu.ifsp.demo_clean.model;


import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @ManyToOne
    public Usuario usuario;

    @ManyToOne
    public Estoque exemplar;

    public LocalDate dataEmprestimo;

    public LocalDate dataVencimento;

    public LocalDate dataDevolucao;

    public Emprestimo(Usuario usuario, Estoque exemplar, LocalDate dataEmprestimo, LocalDate dataVencimento, LocalDate dataDevolucao) {
        this.usuario = usuario;
        this.exemplar = exemplar;
        this.dataEmprestimo = dataEmprestimo;
        this.dataVencimento = dataVencimento;
        this.dataDevolucao = dataDevolucao;
    }

    public boolean emprestimoFinalizado() {
        return dataDevolucao != null;
    }
}
