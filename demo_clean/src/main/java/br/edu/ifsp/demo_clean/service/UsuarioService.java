package br.edu.ifsp.demo_clean.service;

import br.edu.ifsp.demo_clean.dto.UsuarioDTO;
import br.edu.ifsp.demo_clean.model.Usuario;
import br.edu.ifsp.demo_clean.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    private void verificarCpfExistente(String cpf){
        if(this.usuarioRepository.findByCpf(cpf).isPresent()){
            throw new Error("CPF já cadastrado: " + cpf);
        }
    }

    @Transactional
    public Usuario addUsuario(UsuarioDTO dto) {
        verificarCpfExistente(dto.cpf);

        Usuario usuario = new Usuario(
                dto.nome,
                dto.cpf,
                dto.categoria,
                dto.curso,
                dto.status
        );

        return usuarioRepository.save(usuario);
    }


    public String listarUsuarios() {
        List<Usuario> lista = usuarioRepository.findAll();
        if(lista.isEmpty()){
            return "Nenhum usuário encontrado!";
        }

        String resultado = "Usuários cadastrados:\n";
        for(Usuario u : lista){
            resultado += "\n" + u.toString() + "\n";
        }
        return resultado;
    }


    public Usuario buscarUsuario(String cpf){
        Usuario usuario = usuarioRepository.findByCpf(cpf).orElse(null);
        if(usuario == null){
            throw new Error("Usuário não encontrado com CPF: " + cpf);
        }
        return usuario;
    }


    @Transactional
    public Usuario attUsuario(UsuarioDTO dto, String cpf){
        Usuario usuario = usuarioRepository.findByCpf(cpf).orElse(null);
        if(usuario == null){
            throw new Error("Usuário não encontrado com CPF: " + cpf);
        }

        if(dto.nome != null && !dto.nome.isBlank()){
            usuario.setNome(dto.nome);
        }
        if(dto.categoria != null){
            usuario.setCategoria(dto.categoria);
        }
        if(dto.curso != null){
            usuario.setCurso(dto.curso);
        }
        if(dto.status != null){
            usuario.setStatus(dto.status);
        }

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void deletarUsuario(String cpf){
        Usuario usuario = usuarioRepository.findByCpf(cpf).orElse(null);
        if(usuario == null){
            throw new Error("Usuário não encontrado com CPF: " + cpf);
        }
        usuarioRepository.delete(usuario);
    }
}
