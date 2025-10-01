package br.edu.ifsp.demo_clean.controller;

import br.edu.ifsp.demo_clean.dto.UserDTO;
import br.edu.ifsp.demo_clean.model.User;
import br.edu.ifsp.demo_clean.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library/usuarios")
@Tag(name = "Usuario", description = "Responsável por controlar os usuários da biblioteca")
public class UserController {

        private final UserService userService;

        public UserController(UserService userService){
                this.userService = userService;
        }

        @PostMapping
        public String addUser(@RequestBody UserDTO dto){
                try{
                        User newUser = userService.addUser(dto);
                        return "Usuário Cadastrado:\n" + newUser.toString();
                } catch (Error ex){
                        return ex.getMessage();
                }
        }

        @GetMapping
        public String getUsers(){
                return userService.getUsers();
        }

        @GetMapping("/{cpf}")
        public String getUser(@PathVariable String cpf){
                try{
                        User user = userService.getUser(cpf);
                        return user.toString();
                } catch (Error ex){
                        return ex.getMessage();
                }
        }

        @PutMapping("/{cpf}")
        public String updateUser(@RequestBody UserDTO dto, @PathVariable String cpf){
                try{
                        User updatedUser = userService.updateUser(dto, cpf);
                        return "Usuário atualizado:\n" + updatedUser.toString();
                } catch (Error ex){
                        return ex.getMessage();
                }
        }

        @DeleteMapping("/{cpf}")
        public String deleteUser(@PathVariable String cpf){
                try{
                        userService.deleteUser(cpf);
                        return "Usuário deletado com sucesso!";
                } catch (Error ex){
                        return ex.getMessage();
                }
        }
}
