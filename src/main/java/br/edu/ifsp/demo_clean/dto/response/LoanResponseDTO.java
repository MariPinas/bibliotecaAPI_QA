package br.edu.ifsp.demo_clean.dto.response;

import java.time.LocalDate;

import br.edu.ifsp.demo_clean.model.Loan;

public class LoanResponseDTO {
    public UserResponseDTO user;
    public StockResponseDTO stock;
    public LocalDate loanDate;
    public LocalDate dueDate;
    public LocalDate devolutionDate;

    public LoanResponseDTO(Loan loan) {
        this.user = new UserResponseDTO(loan.getUser());
        this.stock = new StockResponseDTO(loan.getStock());
        this.loanDate = loan.getLoanDate();
        this.dueDate = loan.getDueDate();
        this.devolutionDate = loan.getDevolutionDate();
    }
 }
