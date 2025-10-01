package br.edu.ifsp.demo_clean.dto;

import br.edu.ifsp.demo_clean.model.enums.UserCategory;
import br.edu.ifsp.demo_clean.model.enums.Course;
import br.edu.ifsp.demo_clean.model.enums.UserStatus;

public class UserDTO {
    public String name;
    public String cpf;
    public String email;
    public UserCategory category;
    public Course course;
    public UserStatus status;
}
