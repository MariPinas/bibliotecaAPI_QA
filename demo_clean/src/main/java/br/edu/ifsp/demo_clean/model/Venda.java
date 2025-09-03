package br.edu.ifsp.demo_clean.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int codVenda;

    @ManyToOne
    public Cliente comprador;

    @ManyToOne
    public Carro carro;

    public LocalDate data;

    public Venda(){}

    public Venda(Cliente cliente, Carro carro){
        comprador=cliente; this.carro=carro; data=LocalDate.now();
    }
}