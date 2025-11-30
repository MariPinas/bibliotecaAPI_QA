package br.edu.ifsp.demo_clean.model;

import br.edu.ifsp.demo_clean.model.enums.*;
import br.edu.ifsp.demo_clean.strategy.LoanStrategy;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserCategory category;

    @OneToMany(mappedBy = "usuario")
    public List<Loan> loans = new ArrayList<>();

    public User() {}

    // Construtor para atributos comuns a todos os usuários
    public User(String name, String cpf, String email, UserCategory category) {
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.category = category;
    }

    public int getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public UserCategory getCategory() { return category; }
    public void setCategory(UserCategory category) { this.category = category; }

    @Transient
    public abstract LoanStrategy<?> getLoanStrategy();

    public int allActiveLoans() {
        return this.loans.stream().filter(loan -> !loan.emprestimoFinalizado()).toList().size();
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + name + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                ", categoria=" + category +
                '}';
    }
}
