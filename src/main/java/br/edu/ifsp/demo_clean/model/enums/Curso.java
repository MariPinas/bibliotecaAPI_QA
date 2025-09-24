package br.edu.ifsp.demo_clean.model.enums;

public enum Curso {
    ADS,
    PEDAGOGIA,
    ADMINISTRACAO;

    public boolean livroRelacionado(CategoriaLivro categoriaLivro) {
        return switch (this) {
            case ADS -> categoriaLivro.equals(CategoriaLivro.COMPUTACAO);
            case PEDAGOGIA -> categoriaLivro.equals(CategoriaLivro.LETRAS);
            case ADMINISTRACAO -> categoriaLivro.equals(CategoriaLivro.GESTAO);
        };
    }
}
