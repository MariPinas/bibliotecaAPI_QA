package br.edu.ifsp.demo_clean.model;

import br.edu.ifsp.demo_clean.factory.UserRegistry;
import br.edu.ifsp.demo_clean.strategy.*;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("PROFESSOR")
public class Professor extends User {
    static {
        UserRegistry.register(Professor.class, dto ->
                new Professor(dto.name, dto.cpf, dto.email)
        );
    }

    public Professor() { super(); }

    public Professor(String name, String cpf, String email) {
        super(name, cpf, email);
    }



    @Override
    public LoanStrategy<?> getLoanStrategy() {
        var maxBooks = 5;
        var loanDays = 40;
        LoanPolicy policy = new LoanPolicy(maxBooks, loanDays);
        return new ProfessorLoanStrategy(policy);
    }
}
