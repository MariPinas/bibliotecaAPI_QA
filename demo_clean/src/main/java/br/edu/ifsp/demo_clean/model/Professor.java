package br.edu.ifsp.demo_clean.model;

public class Professor extends Usuario{
    private String areaDePesquisa;

    public Professor(String nome, String cpf, String areaDePesquisa){
        super(nome,cpf);
        this.areaDePesquisa=areaDePesquisa;
    }

    @Override
    public String getTipo() {
        return "Professor";
    }

    public String getAreaDePesquisa() {
        return areaDePesquisa;
    }

    public void setAreaDePesquisa(String areaDePesquisa) {
        this.areaDePesquisa = areaDePesquisa;
    }
}
