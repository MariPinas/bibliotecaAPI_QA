package br.edu.ifsp.demo_clean.service;

import br.edu.ifsp.demo_clean.dto.BookRequestDTO;
import br.edu.ifsp.demo_clean.model.Book;
import br.edu.ifsp.demo_clean.repository.BookRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void checkISBN(int isbn) {
        if (this.bookRepository.findByIsbn(isbn) != null) {
            throw new IllegalArgumentException("ISBN já cadastrado");
        }
    }

    public void checkAuthorPublisherEdition(String author, String publisher, String edition) {
        if (this.bookRepository.findByAuthorAndPublisherAndEdition(author, publisher, edition) != null) {
            throw new IllegalArgumentException("ERRO: Dados de book já cadastrados!!!");
        }
    }

    public Book addBook(BookRequestDTO bookRequestDTO) {
        checkISBN(bookRequestDTO.isbn);
        checkAuthorPublisherEdition(
                bookRequestDTO.author,
                bookRequestDTO.publisher,
                bookRequestDTO.edition
        );

        Book novoBook = new Book(
                bookRequestDTO.isbn,
                bookRequestDTO.title,
                bookRequestDTO.author,
                bookRequestDTO.publisher,
                bookRequestDTO.edition,
                bookRequestDTO.category
        );

        return this.bookRepository.save(novoBook);
    }

    public List<Book> listBooks() {
        return bookRepository.findAll();
    }

    public Book getBookByISBN(int isbn) {
        return this.bookRepository.findByIsbn(isbn);
    }

    public Book updateBook(BookRequestDTO bookRequestDTO, int isbn) {
        Book bookAtual = this.bookRepository.findByIsbn(isbn);

        if (bookAtual != null) {
            if (bookRequestDTO.isbn != bookAtual.getIsbn()) {
                checkISBN(bookRequestDTO.isbn);
            }

            checkAuthorPublisherEdition(
                    bookRequestDTO.author,
                    bookRequestDTO.publisher,
                    bookRequestDTO.edition
            );

            bookAtual.setIsbn(bookRequestDTO.isbn);
            bookAtual.setTitle(bookRequestDTO.title);
            bookAtual.setAuthor(bookRequestDTO.author);
            bookAtual.setEdition(bookRequestDTO.edition);
            bookAtual.setPublisher(bookRequestDTO.publisher);
            bookAtual.setCategory(bookRequestDTO.category);

            return this.bookRepository.save(bookAtual);
        } else {
            throw new IllegalArgumentException("Book não encontrado com o isbn: " + isbn);
        }
    }

    public Book deleteBookByISBN(int isbn) {
        final Book book = this.bookRepository.findByIsbn(isbn);

        if (book != null) {
            this.bookRepository.delete(book);
            return book;
        } else {
            throw new IllegalArgumentException("Book não encontrado");
        }
    }
}