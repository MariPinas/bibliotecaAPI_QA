package br.edu.ifsp.demo_clean.repository;

import br.edu.ifsp.demo_clean.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Book findByIsbn(int isbn);
    Book findByAuthorAndPublisherAndEdition(String author, String publisher, String edition);

}