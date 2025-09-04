package br.edu.ifsp.demo_clean.repository;

import br.edu.ifsp.demo_clean.model.Livro;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Integer> {
    Livro findByIsbn(int isbn);
}