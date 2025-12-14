package br.edu.ifsp.demo_clean.service;

import br.edu.ifsp.demo_clean.dto.BookRequestDTO;
import br.edu.ifsp.demo_clean.model.Book;
import br.edu.ifsp.demo_clean.model.enums.BookCategory;
import br.edu.ifsp.demo_clean.repository.BookRepository;
import br.edu.ifsp.demo_clean.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private BookRequestDTO dto;

    @BeforeEach
    void setUp() {
        dto = new BookRequestDTO();
        dto.isbn = 123;
        dto.title = "Livro Teste";
        dto.author = "David Barbosa";
        dto.publisher = "IFSP";
        dto.edition = "1";
        dto.category = BookCategory.COMPUTING;
    }

    @Test
    @DisplayName("addBook - should save book when data is valid")
    void addBook_shouldSaveBook() {
        when(bookRepository.findByIsbn(dto.isbn)).thenReturn(null);
        when(bookRepository.findByAuthorAndPublisherAndEdition(
                dto.author, dto.publisher, dto.edition))
                .thenReturn(null);

        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Book result = bookService.addBook(dto);

        assertNotNull(result);
        assertEquals(dto.isbn, result.getIsbn());
        assertEquals(dto.title, result.getTitle());

        verify(bookRepository).save(any(Book.class));
    }

    @Test
    @DisplayName("addBook - should throw exception when ISBN already exists")
    void addBook_shouldThrowWhenIsbnExists() {
        when(bookRepository.findByIsbn(dto.isbn)).thenReturn(mock(Book.class));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> bookService.addBook(dto));

        assertEquals("ISBN já cadastrado", ex.getMessage());
        verify(bookRepository, never()).save(any());
    }

    @Test
    @DisplayName("addBook - should throw exception when book data already exists")
    void addBook_shouldThrowWhenAuthorPublisherEditionExists() {
        when(bookRepository.findByIsbn(dto.isbn)).thenReturn(null);
        when(bookRepository.findByAuthorAndPublisherAndEdition(
                dto.author, dto.publisher, dto.edition))
                .thenReturn(mock(Book.class));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> bookService.addBook(dto));

        assertEquals("ERRO: Dados de book já cadastrados!!!", ex.getMessage());
    }


    @Test
    @DisplayName("getBookByISBN - should return book")
    void getBookByISBN_shouldReturnBook() {
        Book book = mock(Book.class);
        when(bookRepository.findByIsbn(123)).thenReturn(book);

        Book result = bookService.getBookByISBN(123);

        assertEquals(book, result);
    }

    @Test
    @DisplayName("updateBook - should update book when exists")
    void updateBook_shouldUpdateBook() {
        Book existing = new Book(123, "Livro Existente", "David Barbosa", "Editora Y", "1", BookCategory.COMPUTING);

        when(bookRepository.findByIsbn(123)).thenReturn(existing);
        when(bookRepository.findByIsbn(dto.isbn)).thenReturn(existing);
        when(bookRepository.findByAuthorAndPublisherAndEdition(
                dto.author, dto.publisher, dto.edition))
                .thenReturn(null);

        when(bookRepository.save(any(Book.class))).thenAnswer(i -> i.getArgument(0));

        Book updated = bookService.updateBook(dto, 123);

        assertEquals(dto.title, updated.getTitle());
        verify(bookRepository).save(existing);
    }

    @Test
    @DisplayName("updateBook - should throw when book does not exist")
    void updateBook_shouldThrowWhenNotFound() {
        when(bookRepository.findByIsbn(123)).thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> bookService.updateBook(dto, 123));

        assertEquals("Book não encontrado com o isbn: 123", ex.getMessage());
    }

    @Test
    @DisplayName("deleteBookByISBN - should delete when exists")
    void deleteBook_shouldDelete() {
        Book book = mock(Book.class);
        when(bookRepository.findByIsbn(123)).thenReturn(book);

        Book deleted = bookService.deleteBookByISBN(123);

        assertEquals(book, deleted);
        verify(bookRepository).delete(book);
    }

    @Test
    @DisplayName("deleteBookByISBN - should throw when not found")
    void deleteBook_shouldThrowWhenNotFound() {
        when(bookRepository.findByIsbn(123)).thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> bookService.deleteBookByISBN(123));

        assertEquals("Book não encontrado", ex.getMessage());
    }

    @Test
    @DisplayName("listAllBooks - should return a list of all books")
    void listBooks_shouldReturnAllBooks() {
        List<Book> books = List.of(new Book(), new Book());
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.listBooks();

        assertEquals(2, result.size());
    }
}


