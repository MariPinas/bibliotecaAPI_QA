package br.edu.ifsp.demo_clean.model;

import br.edu.ifsp.demo_clean.model.enums.Curso;

public class Aluno extends Usuario{
    public Curso getCurso() {
        return curso;
    }
    public Aluno(String nome, String cpf, Curso curso) {
        super(nome, cpf);
        this.curso = curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    private Curso curso;


    @Override
    public String getTipo() {
        return "Aluno";
    }
}
