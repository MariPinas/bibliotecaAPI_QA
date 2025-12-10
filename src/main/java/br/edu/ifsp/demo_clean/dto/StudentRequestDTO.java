package br.edu.ifsp.demo_clean.dto;

import br.edu.ifsp.demo_clean.model.enums.Course;
import br.edu.ifsp.demo_clean.model.enums.UserStatus;

public class StudentRequestDTO extends UserRequestDTO {
    public Course course;
    public UserStatus status;
}
