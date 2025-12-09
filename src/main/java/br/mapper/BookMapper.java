package br.mapper;

import br.edu.ifsp.demo_clean.dto.response.BookResponseDTO;
import br.edu.ifsp.demo_clean.model.Book;

public class BookMapper {
    public static BookResponseDTO toDTO(Book book) {
    return new BookResponseDTO(
                book.getId(),
                book.getIsbn(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublisher(),
                book.getEdition(),
                book.getCategory()
            
            );
    }
}