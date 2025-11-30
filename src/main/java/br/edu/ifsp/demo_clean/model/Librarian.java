package br.edu.ifsp.demo_clean.model;

import br.edu.ifsp.demo_clean.model.enums.UserCategory;
import br.edu.ifsp.demo_clean.strategy.*;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("LIBRARIAN")
public class Librarian extends User {

    public Librarian() { super(); }

    public Librarian(String name, String cpf, String email) {
        super(name, cpf, email, UserCategory.LIBRARIAN);
    }

    @Override
    public LoanStrategy<?> getLoanStrategy() {
        LoanPolicy policy = new LoanPolicy(getCategory().maximoLivrosEmprestados(), getCategory().tempoEmprestimo());
        return new LibrarianLoanStrategy(policy);
    }
}
