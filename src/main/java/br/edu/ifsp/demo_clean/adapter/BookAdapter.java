package br.edu.ifsp.demo_clean.adapter;

import br.edu.ifsp.demo_clean.dto.response.BookDTO;
import br.edu.ifsp.demo_clean.interfaces.DTOAdapter;
import br.edu.ifsp.demo_clean.model.Livro;

import java.util.List;

public class BookAdapter implements DTOAdapter<BookDTO, Livro> {

    private BookDTO parse(Livro book) {
        return new BookDTO(book.id, book.isbn, book.autor, book.autor, book.editora, book.edicao, book.categoria);
    }

    @Override
    public BookDTO toDTO(Livro value) {
        return parse(value);
    }

    @Override
    public List<BookDTO> toDTOs(List<Livro> values) {
        return values.stream().map(this::parse).toList();
    }
}
