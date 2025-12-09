package br.edu.ifsp.demo_clean.service;

import br.edu.ifsp.demo_clean.dto.BookDTO;
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
            throw new Error("ISBN já cadastrado");
        }
    }

    public void checkAuthorPublisherEdition(String author, String publisher, String edition) {
        if (this.bookRepository.findByAuthorAndPublisherAndEdition(author, publisher, edition) != null) {
            throw new Error("ERRO: Dados de book já cadastrados!!!");
        }
    }

    public Book addBook(BookDTO bookDTO) {
        checkISBN(bookDTO.isbn);
        checkAuthorPublisherEdition(
                bookDTO.author,
                bookDTO.publisher,
                bookDTO.edition
        );

        Book novoBook = new Book(
                bookDTO.isbn,
                bookDTO.title,
                bookDTO.author,
                bookDTO.publisher,
                bookDTO.edition,
                bookDTO.category
        );

        return this.bookRepository.save(novoBook);
    }

    public List<Book> listBooks() {
        return bookRepository.findAll();
    }

    public Book getBookByISBN(int isbn) {
        return this.bookRepository.findByIsbn(isbn);
    }

    public Book updateBook(BookDTO bookDTO, int isbn) {
        Book bookAtual = this.bookRepository.findByIsbn(isbn);

        if (bookAtual != null) {
            if (bookDTO.isbn != bookAtual.getIsbn()) {
                checkISBN(bookDTO.isbn);
            }

            checkAuthorPublisherEdition(
                    bookDTO.author,
                    bookDTO.publisher,
                    bookDTO.edition
            );

            bookAtual.setIsbn(bookDTO.isbn);
            bookAtual.setTitle(bookDTO.title);
            bookAtual.setAuthor(bookDTO.author);
            bookAtual.setEdition(bookDTO.edition);
            bookAtual.setPublisher(bookDTO.publisher);
            bookAtual.setCategory(bookDTO.category);

            return this.bookRepository.save(bookAtual);
        } else {
            throw new Error("Book não encontrado com o isbn: " + isbn);
        }
    }

    public Book deleteBookByISBN(int isbn) {
        final Book book = this.bookRepository.findByIsbn(isbn);

        if (book != null) {
            this.bookRepository.delete(book);
            return book;
        } else {
            throw new Error("Book não encontrado");
        }
    }
}