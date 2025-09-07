package br.edu.ifsp.demo_clean.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Estoque {
    @Id
    private int codigoExemplar;

    @Column(nullable = false)
    private boolean disponibilidade;

    @ManyToOne
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

    public Estoque(int codigoExemplar, boolean disponibilidade) {
        this.codigoExemplar = codigoExemplar;
        this.disponibilidade = disponibilidade;
    }
}