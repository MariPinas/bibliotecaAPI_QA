package br.edu.ifsp.demo_clean.model;

import br.edu.ifsp.demo_clean.model.enums.Course;
import br.edu.ifsp.demo_clean.model.enums.UserStatus;
import br.edu.ifsp.demo_clean.strategy.LoanStrategy;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Course course;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @OneToMany(mappedBy = "user")
    public List<Loan> loans = new ArrayList<>();

    public User() {
    }

    // Construtor para atributos comuns a todos os usuários
    public User(String name, String cpf, String email, Course course, UserStatus status) {
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.course = course;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    @Transient
    public abstract LoanStrategy<?> getLoanStrategy();

    public int allActiveLoans() {
        return this.loans.stream().filter(loan -> !loan.isCompleted()).toList().size();
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + name + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
