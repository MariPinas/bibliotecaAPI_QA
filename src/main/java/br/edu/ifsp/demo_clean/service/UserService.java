package br.edu.ifsp.demo_clean.service;

import br.edu.ifsp.demo_clean.dto.UserDTO;
import br.edu.ifsp.demo_clean.factory.UserRegistry;
import br.edu.ifsp.demo_clean.model.*;
import br.edu.ifsp.demo_clean.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    private void checkCpf(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")) {
            throw new Error("CPF deve conter exatamente 11 dígitos numéricos.");
        }

        if (cpf.chars().distinct().count() == 1) {
            throw new Error("CPF inválido: sequência repetida.");
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
            throw new Error("CPF inválido: primeiro dígito verificador incorreto.");
        }

        int sum2 = 0;
        for (int i = 0; i < 10; i++) {
            sum2 += numbers[i] * (11 - i);
        }
        int remainder2 = sum2 % 11;
        int digit2 = (remainder2 < 2) ? 0 : 11 - remainder2;
        if (numbers[10] != digit2) {
            throw new Error("CPF inválido: segundo dígito verificador incorreto.");
        }

        if (userRepository.findByCpf(cpf).isPresent()) {
            throw new Error("CPF já cadastrado: " + cpf);
        }
    }

    @Transactional
    public User addUser(UserDTO dto, Class<? extends User> userClass) {
        try{
            checkCpf(dto.cpf);
            User user = UserRegistry.create(dto, userClass);
            return userRepository.save(user);
        }
        catch (Exception e) {
            throw new RuntimeException("Erro ao criar usuário", e);
        }
    }

    public List<User> getUsers() {
    return userRepository.findAll();
}


    public User getUser(String cpf){
        User user = userRepository.findByCpf(cpf).orElse(null);
        if(user == null){
            throw new Error("Usuário não encontrado com CPF: " + cpf);
        }
        return user;
    }

    @Transactional
    public User updateUser(UserDTO dto, String cpf){
        User user = userRepository.findByCpf(cpf).orElse(null);
        if(user == null){
            throw new Error("Usuário não encontrado com CPF: " + cpf);
        }

        if(dto.name != null && !dto.name.isBlank()){
            user.setName(dto.name);
        }
        if(dto.email != null && !dto.email.isBlank()){
            user.setEmail(dto.email);
        }

        if(user instanceof Student){
            Student s = (Student) user;
            if(dto.course != null){
                s.setCourse(dto.course);
            }
            if(dto.status != null){
                s.setStatus(dto.status);
            }
        }

        return userRepository.save(user);
    }

    @Transactional
    public User deleteUser(String cpf){
        final User user = userRepository.findByCpf(cpf).orElse(null);
        if(user == null){
            throw new Error("Usuário não encontrado com CPF: " + cpf);
        }
        if(user.allActiveLoans() > 0){
            throw new Error("Usuário não pode ser deletado pois possui empréstimos ativos.");
        }
        userRepository.delete(user);
        return user;
    }
}
