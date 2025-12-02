package br.edu.ifsp.demo_clean.model.enums;

public enum Course {
    ADS,
    PEDAGOGY,
    ADMINISTRATION;

    public boolean livroRelacionado(BookCategory bookCategory) {
        return switch (this) {
            case ADS -> bookCategory.equals(BookCategory.COMPUTING);
            case PEDAGOGY -> bookCategory.equals(BookCategory.LETTERS);
            case ADMINISTRATION -> bookCategory.equals(BookCategory.MANAGEMENT);
        };
    }
}
