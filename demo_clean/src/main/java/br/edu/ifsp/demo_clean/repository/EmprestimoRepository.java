package br.edu.ifsp.demo_clean.repository;

import br.edu.ifsp.demo_clean.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Integer> {
    List<Emprestimo> findAllByDataDevolucaoIsNull();
}
