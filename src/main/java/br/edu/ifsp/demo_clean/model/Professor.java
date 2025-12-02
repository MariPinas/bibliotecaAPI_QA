package br.edu.ifsp.demo_clean.model;

import br.edu.ifsp.demo_clean.model.enums.UserCategory;
import br.edu.ifsp.demo_clean.strategy.*;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("PROFESSOR")
public class Professor extends User {

    public Professor() { super(); }

    public Professor(String name, String cpf, String email) {
        super(name, cpf, email, UserCategory.PROFESSOR);
    }

    @Override
    public LoanStrategy<?> getLoanStrategy() {
        LoanPolicy policy = new LoanPolicy(getCategory().getMaximumBooksBorrowed(), getCategory().getLoanTime());
        return new ProfessorLoanStrategy(policy);
    }
}
