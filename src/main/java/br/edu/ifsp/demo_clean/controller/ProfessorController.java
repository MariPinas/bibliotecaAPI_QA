package br.edu.ifsp.demo_clean.controller;

import br.edu.ifsp.demo_clean.dto.UserRequestDTO;
import br.edu.ifsp.demo_clean.dto.response.UserResponseDTO;
import br.edu.ifsp.demo_clean.model.Professor;
import br.edu.ifsp.demo_clean.model.User;
import br.edu.ifsp.demo_clean.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library/professor")
@Tag(name = "Professores", description = "Responsável por controlar os usuários do tipo professor")
public class ProfessorController extends BaseUserController<UserRequestDTO, UserResponseDTO> {
    public ProfessorController(UserService userService) {
        super(userService);
    }

    @Override
    protected User getParsedUser(UserRequestDTO dto) {
        return new Professor(dto.name, dto.cpf, dto.email, dto.status);
    }

    @Override
    protected UserResponseDTO userToDTO(User user) {
        return new UserResponseDTO(user);
    }
}
