package br.edu.ifsp.demo_clean.model;

import br.edu.ifsp.demo_clean.model.enums.StatusUsuario;

public abstract class Usuario {
    private String nome;
    private String cpf;
    private StatusUsuario status;

    public Usuario(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
        this.status = StatusUsuario.ATIVO;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public StatusUsuario getStatus() {
        return status;
    }

    public void setStatus(StatusUsuario status) {
        this.status = status;
    }


    public abstract String getTipo();
}
