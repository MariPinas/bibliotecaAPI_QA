package br.edu.ifsp.demo_clean.model.enums;

public enum UserCategory {
    ALUNO,
    PROFESSOR,
    BIBLIOTECARIO;

    public int tempoEmprestimo() {
        return switch (this) {
            case ALUNO -> 15;
            case PROFESSOR -> 40;
            case BIBLIOTECARIO -> 0;
        };
    }
    
    public int maximoLivrosEmprestados() {
        return switch (this) {
            case ALUNO -> 3;
            case PROFESSOR -> 5;
            case BIBLIOTECARIO -> 0;
        };
    }
}
