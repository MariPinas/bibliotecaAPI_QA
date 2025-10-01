package br.edu.ifsp.demo_clean.dto.response;

import br.edu.ifsp.demo_clean.model.enums.UserCategory;
import br.edu.ifsp.demo_clean.model.enums.Course;
import br.edu.ifsp.demo_clean.model.enums.UserStatus;

public class UserDTO {
    public final int id;
    public final String name;
    public final String cpf;
    public final String email;
    public final UserCategory category;
    public final Course course;
    public final UserStatus status;

    public UserDTO(int id, String name, String cpf, String email, UserCategory category, Course course, UserStatus status) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.category = category;
        this.course = course;
        this.status = status;
    }
}
