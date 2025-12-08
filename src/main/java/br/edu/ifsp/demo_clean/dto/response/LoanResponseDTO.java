package br.edu.ifsp.demo_clean.dto.response;

import java.time.LocalDate;

import br.edu.ifsp.demo_clean.model.Stock;
import br.edu.ifsp.demo_clean.model.User;

public class LoanResponseDTO {
    private User user;
    private Stock stock;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate devolutionDate;

    public LoanResponseDTO() {}

    public LoanResponseDTO(User user, Stock stock, LocalDate loanDate, LocalDate dueDate, LocalDate devolutionDate) {
        this.user = user;
        this.stock = stock;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.devolutionDate = devolutionDate;
    }

    public User getUser() {
        return user;
    }

    public Stock getStock() {
        return stock;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getDevolutionDate() {
        return devolutionDate;
    }
 }
