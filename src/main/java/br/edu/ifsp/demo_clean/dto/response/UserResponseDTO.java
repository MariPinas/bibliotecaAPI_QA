package br.edu.ifsp.demo_clean.dto.response;

import br.edu.ifsp.demo_clean.model.enums.Course;
import br.edu.ifsp.demo_clean.model.enums.UserStatus;

public class UserResponseDTO {
    public int id;
    public String name;
    public String cpf;
    public String email;
    public Course course;
    public UserStatus status;

    public UserResponseDTO() {}

    public UserResponseDTO(int id, String name, String cpf, String email, Course course, UserStatus status) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.course = course;
        this.status = status;
    }
}
