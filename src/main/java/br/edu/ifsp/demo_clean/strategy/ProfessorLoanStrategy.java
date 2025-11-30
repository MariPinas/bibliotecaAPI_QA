package br.edu.ifsp.demo_clean.strategy;

public class ProfessorLoanStrategy implements LoanStrategy<LoanPolicy> {

    private final LoanPolicy policy;

    public ProfessorLoanStrategy(LoanPolicy policy) {
        this.policy = policy;
    }

    @Override
    public LoanPolicy getPolicy() {
        return policy;
    }
}

