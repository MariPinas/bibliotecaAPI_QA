package br.edu.ifsp.demo_clean.strategy;

import br.edu.ifsp.demo_clean.model.Book;
import br.edu.ifsp.demo_clean.model.Student;
import br.edu.ifsp.demo_clean.model.User;

public class StudentLoanStrategy implements LoanStrategy {
    private final LoanPolicy policy;

    public StudentLoanStrategy(LoanPolicy policy) {
        this.policy = policy;
    }

    @Override
    public int calculateLoanTermInDays(User user, Book book) {
        final Student student = (Student) user;
        return student.getCourse().isRelated(book.getCategory()) ? 30 : policy.getLoanDays();
    }

    @Override
    public int getBookLimit() {
        return policy.getMaxBooks();
    }
}

