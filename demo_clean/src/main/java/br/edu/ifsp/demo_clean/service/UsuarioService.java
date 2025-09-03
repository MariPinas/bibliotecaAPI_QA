package br.edu.ifsp.demo_clean.service;

import br.edu.ifsp.demo_clean.model.Usuario;
import br.edu.ifsp.demo_clean.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    // Usuário
    public void addUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public List<Usuario> todosUsuarios() {
        return usuarioRepository.findAll();
    }
}
