package br.edu.ifsp.demo_clean.controller;

import br.edu.ifsp.demo_clean.dto.UserRequestDTO;
import br.edu.ifsp.demo_clean.dto.response.UserResponseDTO;
import br.edu.ifsp.demo_clean.model.Librarian;
import br.edu.ifsp.demo_clean.model.User;
import br.edu.ifsp.demo_clean.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library/librarian")
@Tag(name = "Bibliotecários", description = "Responsável por controlar os usuários do tipo bibliotecário")
public class LibrarianController extends BaseUserController<UserRequestDTO, UserResponseDTO> {
    public LibrarianController(UserService userService) {
        super(userService);
    }

    @Override
    protected User getParsedUser(UserRequestDTO dto) {
        return new Librarian(dto.name, dto.cpf, dto.email);
    }

    @Override
    protected UserResponseDTO userToDTO(User user) {
        return new UserResponseDTO(user);
    }
}
