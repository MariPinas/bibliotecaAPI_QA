package br.edu.ifsp.demo_clean.controller;

import br.edu.ifsp.demo_clean.dto.UserRequestDTO;
import br.edu.ifsp.demo_clean.dto.response.BaseResponseDTO;
import br.edu.ifsp.demo_clean.dto.response.UserResponseDTO;
import br.edu.ifsp.demo_clean.model.User;
import br.edu.ifsp.demo_clean.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public abstract class BaseUserController<T extends UserRequestDTO, R extends UserResponseDTO> extends BaseController {
    private final UserService userService;

    public BaseUserController(UserService userService) {
        this.userService = userService;
    }

    protected abstract User getParsedUser(T dto);
    protected abstract R userToDTO(User user);

    @PostMapping
    public ResponseEntity<BaseResponseDTO<R>> addUser(@RequestBody T dto) {
        return handleRequest(() -> userToDTO(userService.addUser(getParsedUser(dto))));
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<BaseResponseDTO<R>> getUser(@PathVariable String cpf) {
        return handleRequest(() -> userToDTO(userService.getUser(cpf)));
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<BaseResponseDTO<R>> updateUser(@RequestBody T dto, @PathVariable String cpf) {
        return handleRequest(() -> userToDTO(userService.updateUser(getParsedUser(dto), cpf)));
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<BaseResponseDTO<R>> deleteUser(@PathVariable String cpf) {
        return handleRequest(() -> userToDTO(userService.deleteUser(cpf)));
    }
}
