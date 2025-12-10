package br.edu.ifsp.demo_clean.dto.response;

import br.edu.ifsp.demo_clean.model.User;
import br.edu.ifsp.demo_clean.model.enums.UserStatus;

public class UserResponseDTO {
    public int id;
    public String name;
    public String cpf;
    public String email;
    public UserStatus status;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.cpf = user.getCpf();
        this.email = user.getEmail();
        this.status = user.getStatus();
    }
}
