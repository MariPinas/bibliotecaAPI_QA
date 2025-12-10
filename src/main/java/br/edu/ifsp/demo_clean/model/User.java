package br.edu.ifsp.demo_clean.model;

import br.edu.ifsp.demo_clean.model.enums.UserStatus;
import br.edu.ifsp.demo_clean.strategy.LoanPolicy;
import br.edu.ifsp.demo_clean.strategy.LoanStrategy;
import jakarta.persistence.*;

import java.time.LocalDate;
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

    @OneToMany(mappedBy = "user")
    public List<Loan> loans = new ArrayList<>();

    public User() {
    }

    public User(String name, String cpf, String email) {
        this.name = name;
        this.cpf = cpf;
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
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

    public UserStatus getStatus() {
        final List<Loan> activeLoans = getAllActiveLoans();
        final int overdue = activeLoans.stream().reduce(0, (acc, loan) -> loan.isOverdue() ? acc + 1 : acc, Integer::sum);

        if(overdue > 1) {
            return UserStatus.SUSPENDED;
        }

        if(overdue > 0) {
            return UserStatus.INACTIVE;
        }

        return UserStatus.ACTIVE;
    }


    @Transient
    public abstract LoanStrategy getLoanStrategy();

    public List<Loan> getAllActiveLoans() {
        return this.loans.stream().filter(loan -> !loan.isCompleted()).toList();
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
