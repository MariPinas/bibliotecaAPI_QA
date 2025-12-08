package br.edu.ifsp.demo_clean.dto.response;

import br.edu.ifsp.demo_clean.model.enums.Course;
import br.edu.ifsp.demo_clean.model.enums.UserStatus;

public class UserResponseDTO {
    private int id;
    private String name;
    private String cpf;
    private String email;
    private Course course;
    private UserStatus status;

    public UserResponseDTO() {}

    public UserResponseDTO(int id, String name, String cpf, String email, Course course, UserStatus status) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.course = course;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public Course getCourse() {
        return course;
    }

    public UserStatus getStatus() {
        return status;
    }
}
