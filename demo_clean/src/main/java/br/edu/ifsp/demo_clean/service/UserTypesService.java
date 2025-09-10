package br.edu.ifsp.demo_clean.service;

import br.edu.ifsp.demo_clean.dto.UsuarioDTO;
import br.edu.ifsp.demo_clean.model.Usuario;
import br.edu.ifsp.demo_clean.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.edu.ifsp.demo_clean.model.enums.CategoriaUsuario;

import java.util.List;

@Service
public class UserTypesService {

    public String listUserTypes() {
        String userTypes = CategoriaUsuario.ToString();

        return userTypes;
    }
}
