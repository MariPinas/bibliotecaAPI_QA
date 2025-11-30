package br.edu.ifsp.demo_clean.strategy;

public class LoanPolicy {
    private final int maxBooks;
    private final int loanDays;

    public LoanPolicy(int maxBooks, int loanDays) {
        this.maxBooks = maxBooks;
        this.loanDays = loanDays;
    }

    public int getMaxBooks() { return maxBooks; }
    public int getLoanDays() { return loanDays; }

    @Override
    public String toString() {
        return "LoanPolicy{" +
                "maxBooks=" + maxBooks +
                ", loanDays=" + loanDays +
                '}';
    }
}

