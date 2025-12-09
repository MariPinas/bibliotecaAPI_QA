package br.edu.ifsp.demo_clean.dto.response;

import java.time.LocalDate;

import br.edu.ifsp.demo_clean.model.Stock;
import br.edu.ifsp.demo_clean.model.User;

public class LoanResponseDTO {
    public User user;
    public Stock stock;
    public LocalDate loanDate;
    public LocalDate dueDate;
    public LocalDate devolutionDate;

    public LoanResponseDTO() {}

    public LoanResponseDTO(User user, Stock stock, LocalDate loanDate, LocalDate dueDate, LocalDate devolutionDate) {
        this.user = user;
        this.stock = stock;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.devolutionDate = devolutionDate;
    }
 }
