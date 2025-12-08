package br.mapper;

import br.edu.ifsp.demo_clean.dto.response.UserResponseDTO;
import br.edu.ifsp.demo_clean.model.User;

public class UserMapper {
    public static UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getCpf(),
                user.getEmail(),
                user.getCourse(),
                user.getStatus()
        );
    }
}
