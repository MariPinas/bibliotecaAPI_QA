package br.edu.ifsp.demo_clean.model;

import br.edu.ifsp.demo_clean.factory.UserRegistry;
import br.edu.ifsp.demo_clean.strategy.*;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("LIBRARIAN")
public class Librarian extends User {

    static {
        UserRegistry.register(Librarian.class, dto ->
                new Librarian(dto.name, dto.cpf, dto.email)
        );
    }

    public Librarian() { super(); }

    public Librarian(String name, String cpf, String email) {
        super(name, cpf, email);
    }

    @Override
    public LoanStrategy<?> getLoanStrategy() {
        var maxBooks = 0;
        var loanDays = 0;
        LoanPolicy policy = new LoanPolicy(maxBooks, loanDays);
        return new LibrarianLoanStrategy(policy);
    }
}
