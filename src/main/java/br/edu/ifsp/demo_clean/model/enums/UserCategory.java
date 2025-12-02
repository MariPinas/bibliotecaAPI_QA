package br.edu.ifsp.demo_clean.model.enums;

public enum UserCategory {
    STUDENT,
    PROFESSOR,
    LIBRARIAN;

    public int getLoanTime() {
        return switch (this) {
            case STUDENT -> 15;
            case PROFESSOR -> 40;
            case LIBRARIAN -> 0;
        };
    }
    
    public int getMaximumBooksBorrowed() {
        return switch (this) {
            case STUDENT -> 3;
            case PROFESSOR -> 5;
            case LIBRARIAN -> 0;
        };
    }
}
