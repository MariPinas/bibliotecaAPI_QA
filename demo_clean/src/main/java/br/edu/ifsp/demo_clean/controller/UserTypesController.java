package br.edu.ifsp.demo_clean.controller;

import br.edu.ifsp.demo_clean.dto.UsuarioDTO;
import br.edu.ifsp.demo_clean.model.Usuario;
import br.edu.ifsp.demo_clean.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@Tag(name = "Usuario", description = "Responsável por controlar os usuários da biblioteca")
public class UserTypesController {

    private final UserCatalogService userCatalogService;

    public UserTypesController(UserCatalogService userCatalogService){
        this.userCatalogService = userCatalogService;
    }



    @GetMapping("/user-categories")
    public String UserTypes(){

        return userCatalogService.listUserTypes();
    }
}
