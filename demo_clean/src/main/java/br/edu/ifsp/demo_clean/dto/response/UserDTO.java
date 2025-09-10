package br.edu.ifsp.demo_clean.dto.response;

import br.edu.ifsp.demo_clean.model.enums.CategoriaUsuario;
import br.edu.ifsp.demo_clean.model.enums.Curso;
import br.edu.ifsp.demo_clean.model.enums.StatusUsuario;

public class UserDTO {
    public final int id;
    public final String name;
    public final String cpf;
    public final String email;
    public final CategoriaUsuario category;
    public final Curso course;
    public final StatusUsuario status;

    public UserDTO(int id, String name, String cpf, String email, CategoriaUsuario category, Curso course, StatusUsuario status) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.category = category;
        this.course = course;
        this.status = status;
    }
}
