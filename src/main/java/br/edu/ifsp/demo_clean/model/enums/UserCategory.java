package br.edu.ifsp.demo_clean.model.enums;

public enum UserCategory {
    STUDENT,
    PROFESSOR,
    LIBRARIAN;

    public int tempoEmprestimo() {
        return switch (this) {
            case STUDENT -> 15;
            case PROFESSOR -> 40;
            case LIBRARIAN -> 0;
        };
    }
    
    public int maximoLivrosEmprestados() {
        return switch (this) {
            case STUDENT -> 3;
            case PROFESSOR -> 5;
            case LIBRARIAN -> 0;
        };
    }
}
