package br.edu.ifsp.demo_clean.controller;

import br.edu.ifsp.demo_clean.dto.UsuarioDTO;
import br.edu.ifsp.demo_clean.model.Usuario;
import br.edu.ifsp.demo_clean.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library/usuarios")
@Tag(name = "Usuario", description = "Responsável por controlar os usuários da biblioteca")
public class UsuarioController {

        private final UsuarioService usuarioService;

        public UsuarioController(UsuarioService usuarioService){
                this.usuarioService = usuarioService;
        }

        @PostMapping
        public String addUsuario(@RequestBody UsuarioDTO dto){
                try{
                        Usuario novoUsuario = usuarioService.addUsuario(dto);
                        return "Usuário Cadastrado:\n" + novoUsuario.toString();
                } catch (Error ex){
                        return ex.getMessage();
                }
        }

        @GetMapping
        public String listarUsuarios(){
                return usuarioService.listarUsuarios();
        }

        @GetMapping("/{cpf}")
        public String buscarUsuario(@PathVariable String cpf){
                try{
                        Usuario usuario = usuarioService.buscarUsuario(cpf);
                        return usuario.toString();
                } catch (Error ex){
                        return ex.getMessage();
                }
        }

        @PutMapping("/{cpf}")
        public String attUsuario(@RequestBody UsuarioDTO dto, @PathVariable String cpf){
                try{
                        Usuario usuarioAtualizado = usuarioService.attUsuario(dto, cpf);
                        return "Usuário atualizado:\n" + usuarioAtualizado.toString();
                } catch (Error ex){
                        return ex.getMessage();
                }
        }

        @DeleteMapping("/{cpf}")
        public String deletarUsuario(@PathVariable String cpf){
                try{
                        usuarioService.deletarUsuario(cpf);
                        return "Usuário deletado com sucesso!";
                } catch (Error ex){
                        return ex.getMessage();
                }
        }
}
