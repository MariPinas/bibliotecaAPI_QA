package br.edu.ifsp.demo_clean.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.edu.ifsp.demo_clean.dto.response.BaseResponseDTO;
import br.edu.ifsp.demo_clean.dto.response.BookResponseDTO;
import br.edu.ifsp.demo_clean.dto.response.LoanResponseDTO;
import br.edu.ifsp.demo_clean.dto.response.StockResponseDTO;
import br.edu.ifsp.demo_clean.dto.response.UserResponseDTO;
import br.edu.ifsp.demo_clean.model.Book;
import br.edu.ifsp.demo_clean.model.Loan;
import br.edu.ifsp.demo_clean.model.Stock;
import br.edu.ifsp.demo_clean.model.Student;
import br.edu.ifsp.demo_clean.model.User;
import br.edu.ifsp.demo_clean.service.BookService;
import br.edu.ifsp.demo_clean.service.LoanService;
import br.edu.ifsp.demo_clean.service.StockService;
import br.edu.ifsp.demo_clean.service.UserService;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ReportControllerTest {
    @Mock
    private BookService bookService;

    @Mock
    private UserService userService;

    @Mock
    private StockService stockService;

    @Mock
    private LoanService loanService;

    @InjectMocks
    private ReportController reportController;

    private Book book;
    private User user;
    private Stock stock;
    private Loan loan;

    @BeforeEach
    void setUp() {
        book = new Book();
        user = new Student();

        stock = new Stock(1, true, book);

        loan = new Loan();
        loan.setUser(user);
        loan.setStock(stock);
        loan.setLoanDate(LocalDate.now().minusDays(5));
        loan.setDueDate(LocalDate.now().plusDays(5));
        loan.setDevolutionDate(null);
    }

    @Test
    @DisplayName("GET /library/report/users")
    void getUsers_shouldReturnOk() {
        when(userService.getUsers()).thenReturn(List.of(user));

        ResponseEntity<BaseResponseDTO<List<UserResponseDTO>>> response = reportController.getUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().data);

        verify(userService, times(1)).getUsers();
    }

    @Test
    @DisplayName("GET /library/report/users - error")
    void getUsers_shouldReturnBadRequestOnError() {
        String errorMessage = "Erro ao listar usuários";
        when(userService.getUsers()).thenThrow(new RuntimeException(errorMessage));

        ResponseEntity<BaseResponseDTO<List<UserResponseDTO>>> response = reportController.getUsers();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(errorMessage, response.getBody().message);
    }

    @Test
    @DisplayName("GET /library/report/books")
    void getBooks_shouldReturnOk() {
        when(bookService.listBooks()).thenReturn(List.of(book));

        ResponseEntity<BaseResponseDTO<List<BookResponseDTO>>> response = reportController.getBooks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().data);

        verify(bookService, times(1)).listBooks();
    }

    @Test
    @DisplayName("GET /library/report/stock")
    void getStock_shouldReturnOk() {
        when(stockService.getAllAvailable()).thenReturn(List.of(stock));

        ResponseEntity<BaseResponseDTO<List<StockResponseDTO>>> response = reportController.getStock();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().data);

        verify(stockService, times(1)).getAllAvailable();
    }

    @Test
    @DisplayName("GET /library/report/loan/assets")
    void getAssets_shouldReturnOk() {
        when(loanService.listAssets()).thenReturn(List.of(loan));

        ResponseEntity<BaseResponseDTO<List<LoanResponseDTO>>> response = reportController.getAssets();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().data);

        verify(loanService, times(1)).listAssets();
    }

    @Test
    @DisplayName("GET /library/report/loan/history")
    void getHistory_shouldReturnOk() {
        when(loanService.listHistory()).thenReturn(List.of(loan));

        ResponseEntity<BaseResponseDTO<List<LoanResponseDTO>>> response = reportController.getHistory();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().data);

        verify(loanService, times(1)).listHistory();
    }

}
