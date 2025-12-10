package br.edu.ifsp.demo_clean.controller;

import br.edu.ifsp.demo_clean.dto.StudentRequestDTO;
import br.edu.ifsp.demo_clean.dto.UserRequestDTO;
import br.edu.ifsp.demo_clean.dto.response.StudentResponseDTO;
import br.edu.ifsp.demo_clean.dto.response.UserResponseDTO;
import br.edu.ifsp.demo_clean.model.Student;
import br.edu.ifsp.demo_clean.model.User;
import br.edu.ifsp.demo_clean.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library/student")
@Tag(name = "Estudantes", description = "Responsável por controlar os usuários do tipo estudante")
public class StudentController extends BaseUserController<StudentRequestDTO, StudentResponseDTO> {
    public StudentController(UserService userService) {
        super(userService);
    }

    @Override
    protected User getParsedUser(StudentRequestDTO dto) {
        return new Student(dto.name, dto.cpf, dto.email, dto.status, dto.course);
    }

    @Override
    protected StudentResponseDTO userToDTO(User user) {
        return new StudentResponseDTO((Student) user);
    }
}
