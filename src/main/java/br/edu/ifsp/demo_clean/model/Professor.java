package br.edu.ifsp.demo_clean.model;

import br.edu.ifsp.demo_clean.model.enums.UserStatus;
import br.edu.ifsp.demo_clean.strategy.*;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("PROFESSOR")
public class Professor extends User {
    public Professor() {}

    public Professor(String name, String cpf, String email) {
        super(name, cpf, email);
    }

    @Override
    public LoanStrategy getLoanStrategy() {
        final int maxBooks = 5;
        final int loanDays = 40;

        return new ProfessorLoanStrategy(new LoanPolicy(maxBooks, loanDays));
    }
}
