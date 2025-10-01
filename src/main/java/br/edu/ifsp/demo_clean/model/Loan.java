package br.edu.ifsp.demo_clean.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @ManyToOne
    @JsonBackReference
    public User usuario;

    @ManyToOne
    @JsonBackReference
    public Estoque exemplar;

    public LocalDate dataEmprestimo;

    public LocalDate dataVencimento;

    public LocalDate dataDevolucao;

    public Loan() {}

    public Loan(User usuario, Estoque exemplar, LocalDate dataEmprestimo, LocalDate dataVencimento, LocalDate dataDevolucao) {
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
