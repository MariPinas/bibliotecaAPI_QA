package br.edu.ifsp.demo_clean.controller;

import br.edu.ifsp.demo_clean.dto.UserDTO;
import br.edu.ifsp.demo_clean.model.User;
import br.edu.ifsp.demo_clean.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library/users")
@Tag(name = "Usuários", description = "Responsável por controlar os usuários da biblioteca")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody UserDTO dto) {
        try {
            return ResponseEntity.ok(userService.addUser(dto));
        } catch (Error ex) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        try {
            return ResponseEntity.ok(userService.getUsers());
        } catch (Error ex) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<User> getUser(@PathVariable String cpf) {
        try {
            return ResponseEntity.ok(userService.getUser(cpf));
        } catch (Error ex) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<User> updateUser(@RequestBody UserDTO dto, @PathVariable String cpf) {
        try {
            return ResponseEntity.ok(userService.updateUser(dto, cpf));
        } catch (Error ex) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<User> deleteUser(@PathVariable String cpf) {
        try {
            return ResponseEntity.ok(userService.deleteUser(cpf));
        } catch (Error ex) {
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
