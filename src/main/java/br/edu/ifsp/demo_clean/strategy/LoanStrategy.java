package br.edu.ifsp.demo_clean.strategy;

import br.edu.ifsp.demo_clean.model.Book;
import br.edu.ifsp.demo_clean.model.User;

public interface LoanStrategy {
    int calculateLoanTermInDays(User user, Book book);
    int getBookLimit();
}
