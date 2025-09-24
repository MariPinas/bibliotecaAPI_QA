package br.edu.ifsp.demo_clean.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.ifsp.demo_clean.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByCpf(String cpf);
}
