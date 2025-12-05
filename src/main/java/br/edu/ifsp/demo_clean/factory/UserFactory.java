package br.edu.ifsp.demo_clean.factory;

import br.edu.ifsp.demo_clean.dto.UserDTO;
import br.edu.ifsp.demo_clean.model.User;

public interface UserFactory<T extends User> {
    User create(UserDTO dto);
}
