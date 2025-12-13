package br.edu.ifsp.demo_clean.controller;

import br.edu.ifsp.demo_clean.dto.BookRequestDTO;
import br.edu.ifsp.demo_clean.dto.response.BaseResponseDTO;
import br.edu.ifsp.demo_clean.dto.response.BookResponseDTO;
import br.edu.ifsp.demo_clean.model.Book;
import br.edu.ifsp.demo_clean.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;
    private BookRequestDTO bookRequestDTO;
    private Book mockBook;
    private static final int MOCK_ISBN = 123456;

    @BeforeEach
    void setUp() {
        bookRequestDTO = new BookRequestDTO();
        bookRequestDTO.title = "Livro Teste";
        bookRequestDTO.author = "Autor Teste";
        bookRequestDTO.isbn = MOCK_ISBN;

        mockBook = mock(Book.class);
    }

    @Test
    @DisplayName("POST /library/book")
    void addBook_shouldReturnOk() {
        when(bookService.addBook(bookRequestDTO)).thenReturn(mockBook);

        ResponseEntity<BaseResponseDTO<BookResponseDTO>> response = bookController.addBook(bookRequestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().data);

        verify(bookService, times(1)).addBook(bookRequestDTO);
    }

    @Test
    @DisplayName("POST /library/book")
    void addBook_shouldReturnBadRequestOnError() {
        String errorMessage = "ISBN já cadastrado";
        when(bookService.addBook(bookRequestDTO)).thenThrow(new RuntimeException(errorMessage));

        ResponseEntity<BaseResponseDTO<BookResponseDTO>> response = bookController.addBook(bookRequestDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody().message);

        verify(bookService, times(1)).addBook(bookRequestDTO);
    }

    @Test
    @DisplayName("GET /library/book/{isbn}")
    void getByISBN_shouldReturnOk() {
        when(bookService.getBookByISBN(MOCK_ISBN)).thenReturn(mockBook);

        ResponseEntity<BaseResponseDTO<BookResponseDTO>> response = bookController.getByISBN(MOCK_ISBN);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().data);

        verify(bookService, times(1)).getBookByISBN(MOCK_ISBN);
    }

    @Test
    @DisplayName("GET /library/book/{isbn}")
    void getByISBN_shouldReturnBadRequestOnError() {
        String errorMessage = "Livro não encontrado";
        when(bookService.getBookByISBN(MOCK_ISBN)).thenThrow(new RuntimeException(errorMessage));

        ResponseEntity<BaseResponseDTO<BookResponseDTO>> response = bookController.getByISBN(MOCK_ISBN);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody().message);

        verify(bookService, times(1)).getBookByISBN(MOCK_ISBN);
    }

    @Test
    @DisplayName("PUT /library/book/{isbn}")
    void updateBook_shouldReturnOk() {
        when(bookService.updateBook(bookRequestDTO, MOCK_ISBN)).thenReturn(mockBook);

        ResponseEntity<BaseResponseDTO<BookResponseDTO>> response = bookController.updateBook(bookRequestDTO, MOCK_ISBN);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().data);

        verify(bookService, times(1)).updateBook(bookRequestDTO, MOCK_ISBN);
    }

    @Test
    @DisplayName("PUT /library/book/{isbn}")
    void updateBook_shouldReturnBadRequestOnError() {
        String errorMessage = "Erro ao atualizar livro";
        when(bookService.updateBook(bookRequestDTO, MOCK_ISBN)).thenThrow(new RuntimeException(errorMessage));

        ResponseEntity<BaseResponseDTO<BookResponseDTO>> response = bookController.updateBook(bookRequestDTO, MOCK_ISBN);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody().message);

        verify(bookService, times(1)).updateBook(bookRequestDTO, MOCK_ISBN);
    }

    @Test
    @DisplayName("DELETE /library/book/{isbn}")
    void deleteBook_shouldReturnOk() {
        when(bookService.deleteBookByISBN(MOCK_ISBN)).thenReturn(mockBook);

        ResponseEntity<BaseResponseDTO<BookResponseDTO>> response = bookController.deleteBook(MOCK_ISBN);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().data);

        verify(bookService, times(1)).deleteBookByISBN(MOCK_ISBN);
    }

    @Test
    @DisplayName("DELETE /library/book/{isbn}")
    void deleteBook_shouldReturnBadRequestOnError() {
        String errorMessage = "Livro possui empréstimos";
        when(bookService.deleteBookByISBN(MOCK_ISBN)).thenThrow(new RuntimeException(errorMessage));

        ResponseEntity<BaseResponseDTO<BookResponseDTO>> response = bookController.deleteBook(MOCK_ISBN);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody().message);

        verify(bookService, times(1)).deleteBookByISBN(MOCK_ISBN);
    }
}
