package br.edu.ifsp.demo_clean.service;

import br.edu.ifsp.demo_clean.model.*;
import br.edu.ifsp.demo_clean.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private void checkCpf(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF deve conter exatamente 11 dígitos numéricos.");
        }

        if (cpf.chars().distinct().count() == 1) {
            throw new IllegalArgumentException("CPF inválido: sequência repetida.");
        }

        int[] numbers = new int[11];
        for (int i = 0; i < 11; i++) {
            numbers[i] = cpf.charAt(i) - '0';
        }

        int sum1 = 0;
        for (int i = 0; i < 9; i++) {
            sum1 += numbers[i] * (10 - i);
        }
        int remainder1 = sum1 % 11;
        int digit1 = (remainder1 < 2) ? 0 : 11 - remainder1;
        if (numbers[9] != digit1) {
            throw new IllegalArgumentException("CPF inválido: primeiro dígito verificador incorreto.");
        }

        int sum2 = 0;
        for (int i = 0; i < 10; i++) {
            sum2 += numbers[i] * (11 - i);
        }
        int remainder2 = sum2 % 11;
        int digit2 = (remainder2 < 2) ? 0 : 11 - remainder2;
        if (numbers[10] != digit2) {
            throw new IllegalArgumentException("CPF inválido: segundo dígito verificador incorreto.");
        }

        if (userRepository.findByCpf(cpf).isPresent()) {
            throw new IllegalArgumentException("CPF já cadastrado: " + cpf);
        }
    }

    @Transactional
    public User addUser(User user) {
        checkCpf(user.getCpf());
        return userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }


    public User getUser(String cpf) {
        final User user = userRepository.findByCpf(cpf).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("Usuário não encontrado com CPF: " + cpf);
        }

        return user;
    }

    @Transactional
    public User updateUser(User userFromDTO, String cpf) {
        final User user = userRepository.findByCpf(cpf).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("Usuário não encontrado com CPF: " + cpf);
        }

        userFromDTO.setId(user.getId());
        return userRepository.save(userFromDTO);
    }

    @Transactional
    public User deleteUser(String cpf) {
        final User user = userRepository.findByCpf(cpf).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("Usuário não encontrado com CPF: " + cpf);
        }
        if (!user.getAllActiveLoans().isEmpty()) {
            throw new IllegalArgumentException("Usuário não pode ser deletado pois possui empréstimos ativos.");
        }
        userRepository.delete(user);
        return user;
    }
}
