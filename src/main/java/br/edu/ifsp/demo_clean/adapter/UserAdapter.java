package br.edu.ifsp.demo_clean.adapter;

import br.edu.ifsp.demo_clean.dto.response.UserDTO;
import br.edu.ifsp.demo_clean.interfaces.DTOAdapter;
import br.edu.ifsp.demo_clean.model.User;
import br.edu.ifsp.demo_clean.model.Student;
import br.edu.ifsp.demo_clean.model.enums.Course;
import br.edu.ifsp.demo_clean.model.enums.UserStatus;

import java.util.List;

public class UserAdapter implements DTOAdapter<UserDTO, User> {

    private UserDTO parse(User user) {
        Course course = null;
        UserStatus status = null;
        if (user instanceof Student) {
            Student s = (Student) user;
            course = s.getCourse();
            status = s.getStatus();
        }
        return new UserDTO(user.getId(), user.getName(), user.getCpf(), user.getEmail(), user.getCategory(), course, status);
    }

    @Override
    public UserDTO toDTO(User value) {
        return parse(value);
    }

    @Override
    public List<UserDTO> toDTOs(List<User> values) {
        return values.stream().map(this::parse).toList();
    }
}
