package br.edu.ifsp.demo_clean.factory;

import br.edu.ifsp.demo_clean.dto.UserDTO;
import br.edu.ifsp.demo_clean.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserRegistry {
    private static final Map<Class<? extends User>, UserFactory<? extends User>> creators = new HashMap<>();

    public static <T extends User> void register(Class<T> userClass, UserFactory<T> creator) {
        creators.put(userClass, creator);
    }

    @SuppressWarnings("unchecked")
    public static <T extends User> T create(UserDTO dto, Class<T> userClass) {
        UserFactory<T> creator = (UserFactory<T>) creators.get(userClass);

        return (T) creator.create(dto);
    }
}
