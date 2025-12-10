package br.edu.ifsp.demo_clean.model;

import br.edu.ifsp.demo_clean.model.enums.UserStatus;
import br.edu.ifsp.demo_clean.strategy.*;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("LIBRARIAN")
public class Librarian extends User {
    public Librarian() {}

    public Librarian(String name, String cpf, String email, UserStatus status) {
        super(name, cpf, email, status);
    }

    @Override
    public LoanStrategy getLoanStrategy() {
        final int maxBooks = 0;
        final int loanDays = 0;

        return new LibrarianLoanStrategy(new LoanPolicy(maxBooks, loanDays));
    }
}
