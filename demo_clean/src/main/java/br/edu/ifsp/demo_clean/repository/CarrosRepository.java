package br.edu.ifsp.demo_clean.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsp.demo_clean.model.Carro;

public interface CarrosRepository extends JpaRepository<Carro,Integer>{}
