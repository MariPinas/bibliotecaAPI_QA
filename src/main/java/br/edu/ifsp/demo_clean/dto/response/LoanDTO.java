package br.edu.ifsp.demo_clean.dto.response;

import java.time.LocalDate;

public class LoanDTO {
    public final String bookTitle;
    public final int copyCode;
    public final LocalDate loanDate;
    public final LocalDate dueDate;
    public final LocalDate returnDate;

    public LoanDTO(String bookTitle, int copyCode, LocalDate loanDate, LocalDate dueDate, LocalDate returnDate) {
        this.bookTitle = bookTitle;
        this.copyCode = copyCode;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }
}
