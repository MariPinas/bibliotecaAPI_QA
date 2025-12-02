package br.edu.ifsp.demo_clean.repository;

import br.edu.ifsp.demo_clean.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Integer> {
    List<Stock> findByAvailability(boolean availability);
}
