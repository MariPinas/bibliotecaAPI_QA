package br.edu.ifsp.demo_clean.controller;

import br.edu.ifsp.demo_clean.model.Usuario;
import br.edu.ifsp.demo_clean.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library")
@Tag(name = "Usuario", description = "Responsável por controlar os usuários da biblioteca")
public class UsuarioController {

        private final UsuarioService usuarioService;

        public UsuarioController(UsuarioService usuarioService){
                this.usuarioService = usuarioService;
        }

        @PostMapping("/usuario")
        public String addUsuario(@RequestBody Usuario usuario){
                usuarioService.addUsuario(usuario);
                return "ok usuario";
        }

        @GetMapping("/usuario")
        public List<Usuario> todosUsuarios(){
                return usuarioService.todosUsuarios();
        }
}
