package br.edu.ifsp.demo_clean.model;

import br.edu.ifsp.demo_clean.model.enums.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaUsuario categoria;

    @Enumerated(EnumType.STRING)
    private Curso curso;

    @Enumerated(EnumType.STRING)
    private StatusUsuario status;

    @OneToMany(mappedBy = "usuario")
    public List<Emprestimo> emprestimos = new ArrayList<>();

    public Usuario() {}

    public Usuario(String nome, String cpf, CategoriaUsuario categoria, Curso curso, StatusUsuario status) {
        this.nome = nome;
        this.cpf = cpf;
        this.categoria = categoria;
        this.curso = categoria == CategoriaUsuario.ALUNO ? curso : null;
        this.status = categoria == CategoriaUsuario.ALUNO ? status : null;
    }

    public int getId() { return id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public CategoriaUsuario getCategoria() { return categoria; }
    public void setCategoria(CategoriaUsuario categoria) { this.categoria = categoria; }

    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }

    public StatusUsuario getStatus() { return status; }
    public void setStatus(StatusUsuario status) { this.status = status; }

    public int totalEmprestimosAtivos() {
        return this.emprestimos.stream().filter(emprestimo -> !emprestimo.emprestimoFinalizado()).toList().size();
    }
}
