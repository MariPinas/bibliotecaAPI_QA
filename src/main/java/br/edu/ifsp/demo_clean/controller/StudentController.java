package br.edu.ifsp.demo_clean.controller;

import br.edu.ifsp.demo_clean.model.Student;
import br.edu.ifsp.demo_clean.model.User;
import br.edu.ifsp.demo_clean.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library/student")
@Tag(name = "Estudantes", description = "Responsável por controlar os usuários do tipo estudante")
public class StudentController extends BaseUserController{
    public StudentController(UserService userService) {
        super(userService);
    }

    @Override
    protected Class<? extends User> getUserClass() {
        return Student.class;
    }
}
