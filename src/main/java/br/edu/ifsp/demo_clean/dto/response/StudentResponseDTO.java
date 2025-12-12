package br.edu.ifsp.demo_clean.dto.response;

import br.edu.ifsp.demo_clean.model.Student;
import br.edu.ifsp.demo_clean.model.enums.Course;

public class StudentResponseDTO extends UserResponseDTO {
    public Course course;

    public StudentResponseDTO(Student student) {
        super(student);
        this.course = student.getCourse();
    }
}
