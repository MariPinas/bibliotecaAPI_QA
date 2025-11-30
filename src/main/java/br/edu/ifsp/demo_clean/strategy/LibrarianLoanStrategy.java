package br.edu.ifsp.demo_clean.strategy;

public class LibrarianLoanStrategy implements LoanStrategy<LoanPolicy> {

    private final LoanPolicy policy;

    public LibrarianLoanStrategy(LoanPolicy policy) {
        this.policy = policy;
    }

    @Override
    public LoanPolicy getPolicy() {
        return policy;
    }
}

