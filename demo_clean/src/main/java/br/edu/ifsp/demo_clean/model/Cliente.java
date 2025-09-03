package br.edu.ifsp.demo_clean.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    public String nome;

    @OneToMany(mappedBy="cliente")
    public List<Carro> carros = new ArrayList<>();

    public Cliente(){}

    public Cliente(String nome){
        this.nome=nome;
    }
}
