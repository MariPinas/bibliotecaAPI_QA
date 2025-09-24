package br.edu.ifsp.demo_clean.adapter;

import br.edu.ifsp.demo_clean.dto.response.UserDTO;
import br.edu.ifsp.demo_clean.interfaces.DTOAdapter;
import br.edu.ifsp.demo_clean.model.Usuario;

import java.util.List;

public class UserAdapter implements DTOAdapter<UserDTO, Usuario> {

    private UserDTO parse(Usuario user) {
        return new UserDTO(user.getId(), user.getNome(), user.getCpf(), user.getEmail(), user.getCategoria(), user.getCurso(), user.getStatus());
    }

    @Override
    public UserDTO toDTO(Usuario value) {
        return parse(value);
    }

    @Override
    public List<UserDTO> toDTOs(List<Usuario> values) {
        return values.stream().map(this::parse).toList();
    }
}
