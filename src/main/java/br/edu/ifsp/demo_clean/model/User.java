package br.edu.ifsp.demo_clean.model;

import br.edu.ifsp.demo_clean.model.enums.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class User {
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

    @Enumerated(EnumType.STRING)
    private Course course;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @OneToMany(mappedBy = "usuario")
    public List<Loan> loans = new ArrayList<>();

    public User() {}

    public User(String name, String cpf, String email, UserCategory category, Course course, UserStatus status) {
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.category = category;
        this.course = category == UserCategory.ALUNO ? course : null;
        this.status = category == UserCategory.ALUNO ? status : null;
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

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public UserStatus getStatus() { return status; }
    public void setStatus(UserStatus status) { this.status = status; }

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
                (category == UserCategory.ALUNO ? ", curso=" + course + ", status=" + status : "") +
                '}';
    }
}
