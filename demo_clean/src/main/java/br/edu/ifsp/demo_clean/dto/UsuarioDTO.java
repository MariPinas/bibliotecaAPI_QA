package br.edu.ifsp.demo_clean.dto;

import br.edu.ifsp.demo_clean.model.enums.CategoriaUsuario;
import br.edu.ifsp.demo_clean.model.enums.Curso;
import br.edu.ifsp.demo_clean.model.enums.StatusUsuario;

public class UsuarioDTO {
    public String nome;
    public String cpf;
    public CategoriaUsuario categoria;
    public Curso curso;
    public StatusUsuario status;
}
