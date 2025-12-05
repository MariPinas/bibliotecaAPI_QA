package br.edu.ifsp.demo_clean.controller;

import br.edu.ifsp.demo_clean.model.Librarian;
import br.edu.ifsp.demo_clean.model.User;
import br.edu.ifsp.demo_clean.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library/librarian")
@Tag(name = "Bibliotecários", description = "Responsável por controlar os usuários do tipo bibliotecário")
public class LibrarianController extends BaseUserController{
    public LibrarianController(UserService userService) {
        super(userService);
    }

    @Override
    protected Class<? extends User> getUserClass() {
        return Librarian.class;
    }
}
