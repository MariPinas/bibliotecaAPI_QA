package br.edu.ifsp.demo_clean.strategy;

public interface LoanStrategy<T> {
    LoanPolicy getPolicy();
}
