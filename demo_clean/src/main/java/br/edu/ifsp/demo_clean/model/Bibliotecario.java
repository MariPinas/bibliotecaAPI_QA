package br.edu.ifsp.demo_clean.model;

public class Bibliotecario extends Usuario{
    private String matriculaFuncionario;

    public Bibliotecario(String nome, String cpf, String matriculaFuncionario){
        super(nome, cpf);
        this.matriculaFuncionario=matriculaFuncionario;
    }

    @Override
    public String getTipo() {
        return "Bibliotecario";
    }

    public String getMatriculaFuncionario() {
        return matriculaFuncionario;
    }

    public void setMatriculaFuncionario(String matriculaFuncionario) {
        this.matriculaFuncionario = matriculaFuncionario;
    }
}
