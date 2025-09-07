package br.edu.ifsp.demo_clean.repository;

import br.edu.ifsp.demo_clean.model.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstoqueRepository extends JpaRepository<Estoque, Integer> {
    List<Estoque> findByDisponibilidade(boolean disponibilidade);
}
