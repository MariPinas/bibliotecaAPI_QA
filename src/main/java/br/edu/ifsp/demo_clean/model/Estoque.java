package br.edu.ifsp.demo_clean.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Estoque {
    @Id
    private int codigoExemplar;

    @Column(nullable = false)
    private boolean disponibilidade;

    @ManyToOne
    @JsonBackReference
    public Livro livro;

    public int getCodigoExemplar() {
        return codigoExemplar;
    }

    public boolean getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public Estoque() {
    }

    public Estoque(int codigoExemplar, boolean disponibilidade, Livro livro) {
        this.codigoExemplar = codigoExemplar;
        this.disponibilidade = disponibilidade;
        this.livro = livro;
    }
}