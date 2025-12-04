package br.edu.ifsp.demo_clean.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Stock stock;

    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate devolutionDate;

    public Loan() {
    }

    public Loan(User user, Stock stock, LocalDate loanDate, LocalDate dueDate, LocalDate devolutionDate) {
        this.user = user;
        this.stock = stock;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.devolutionDate = devolutionDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getDevolutionDate() {
        return devolutionDate;
    }

    public void setDevolutionDate(LocalDate devolutionDate) {
        this.devolutionDate = devolutionDate;
    }

    public boolean isCompleted() {
        return devolutionDate != null;
    }
}
