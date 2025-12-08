package br.edu.ifsp.demo_clean.controller;

import br.edu.ifsp.demo_clean.dto.UserDTO;
import br.edu.ifsp.demo_clean.dto.response.UserResponseDTO;
import br.edu.ifsp.demo_clean.model.User;
import br.edu.ifsp.demo_clean.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public abstract class BaseUserController {
    private final UserService userService;

    public BaseUserController(UserService userService) {
        this.userService = userService;
    }

    protected abstract Class<? extends User> getUserClass();

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody UserDTO dto) {
        try {
            return ResponseEntity.ok(userService.addUser(dto, this.getUserClass()));
        } catch (Error ex) {
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable String cpf) {
        try {
            return ResponseEntity.ok(userService.getUser(cpf));
        } catch (Error ex) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody UserDTO dto, @PathVariable String cpf) {
        try {
            return ResponseEntity.ok(userService.updateUser(dto, cpf));
        } catch (Error ex) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<UserResponseDTO> deleteUser(@PathVariable String cpf) {
        try {
            return ResponseEntity.ok(userService.deleteUser(cpf));
        } catch (Error ex) {
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
