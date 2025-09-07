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

    private void verificarCpfExistente(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")) {
            throw new Error("CPF deve conter exatamente 11 dígitos numéricos.");
        }

        if (cpf.chars().distinct().count() == 1) {
            throw new Error("CPF inválido: sequência repetida.");
        }

        int[] numeros = new int[11];
        for (int i = 0; i < 11; i++) {
            numeros[i] = cpf.charAt(i) - '0';
        }

        int soma1 = 0;
        for (int i = 0; i < 9; i++) {
            soma1 += numeros[i] * (10 - i);
        }
        int resto1 = soma1 % 11;
        int digito1 = (resto1 < 2) ? 0 : 11 - resto1;
        if (numeros[9] != digito1) {
            throw new Error("CPF inválido: primeiro dígito verificador incorreto.");
        }

        int soma2 = 0;
        for (int i = 0; i < 10; i++) {
            soma2 += numeros[i] * (11 - i);
        }
        int resto2 = soma2 % 11;
        int digito2 = (resto2 < 2) ? 0 : 11 - resto2;
        if (numeros[10] != digito2) {
            throw new Error("CPF inválido: segundo dígito verificador incorreto.");
        }

        if (usuarioRepository.findByCpf(cpf).isPresent()) {
            throw new Error("CPF já cadastrado: " + cpf);
        }
    }

    @Transactional
    public Usuario addUsuario(UsuarioDTO dto) {
        verificarCpfExistente(dto.cpf);

        Usuario usuario = new Usuario(
                dto.nome,
                dto.cpf,
                dto.email,
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
        if(dto.email != null && !dto.email.isBlank()){
            usuario.setEmail(dto.email);
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
        if(usuario.totalEmprestimosAtivos() > 0){
            throw new Error("Usuário não pode ser deletado pois possui empréstimos ativos.");
        }
        usuarioRepository.delete(usuario);
    }
}
