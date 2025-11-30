package br.edu.ifsp.demo_clean.strategy;

public class StudentLoanStrategy implements LoanStrategy<LoanPolicy> {

    private final LoanPolicy policy;

    public StudentLoanStrategy(LoanPolicy policy) {
        this.policy = policy;
    }

    @Override
    public LoanPolicy getPolicy() {
        return policy;
    }
}

