package br.edu.ifsp.demo_clean.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsp.demo_clean.model.Venda;


public interface ReposVenda extends JpaRepository<Venda,Integer>{}
