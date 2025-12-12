package br.edu.ifsp.demo_clean.strategy;

import br.edu.ifsp.demo_clean.model.Book;
import br.edu.ifsp.demo_clean.model.User;

public class LibrarianLoanStrategy implements LoanStrategy {
    private final LoanPolicy policy;

    public LibrarianLoanStrategy(LoanPolicy policy) {
        this.policy = policy;
    }

    @Override
    public int calculateLoanTermInDays(User user, Book book) {
        return policy.getLoanDays();
    }

    @Override
    public int getBookLimit() {
        return policy.getMaxBooks();
    }
}

