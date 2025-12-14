package br.edu.ifsp.demo_clean.controller;

import br.edu.ifsp.demo_clean.dto.LoanRequestDTO;
import br.edu.ifsp.demo_clean.dto.response.BaseResponseDTO;
import br.edu.ifsp.demo_clean.dto.response.LoanResponseDTO;
import br.edu.ifsp.demo_clean.model.*;
import br.edu.ifsp.demo_clean.model.enums.BookCategory;
import br.edu.ifsp.demo_clean.model.enums.Course;
import br.edu.ifsp.demo_clean.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("LoanController Tests")
class LoanControllerTest {

    @Mock
    private LoanService loanService;

    @InjectMocks
    private LoanController loanController;

    private LoanRequestDTO loanRequestDTO;
    private Loan loan;
    private Student student;
    private Book book;
    private Stock stock;

    @BeforeEach
    void setUp() {
        loanRequestDTO = new LoanRequestDTO();
        loanRequestDTO.stockCode = 1;
        loanRequestDTO.cpf = "12345678901";

        book = new Book(123456, "Clean Code", "Robert Martin", "Seila oque", "1", BookCategory.COMPUTING);
        stock = new Stock(1, true, book);
        student = new Student("Mariana", "12345678901", "mari@gmail.com", Course.ADS);
        loan = new Loan(student, stock, LocalDate.now(), LocalDate.now().plusDays(15), null);
    }

    @Nested
    @DisplayName("Register Loan Tests")
    class RegisterLoanTests {

        @Test
        @DisplayName("Should register loan successfully")
        void shouldRegisterLoanSuccessfully() {
            when(loanService.register(anyInt(), anyString())).thenReturn(loan);

            ResponseEntity<BaseResponseDTO<LoanResponseDTO>> response = loanController.register(loanRequestDTO);

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertTrue(response.getBody().success);
            assertNotNull(response.getBody().data);
            assertEquals("Mariana", response.getBody().data.user.name);
            assertEquals(1, response.getBody().data.stock.code);
            verify(loanService, times(1)).register(1, "12345678901");
        }

        @Test
        @DisplayName("Should return error when stock not found")
        void shouldReturnErrorWhenStockNotFound() {
            when(loanService.register(anyInt(), anyString()))
                    .thenThrow(new IllegalArgumentException("Exemplar não encontrado!"));

            ResponseEntity<BaseResponseDTO<LoanResponseDTO>> response = loanController.register(loanRequestDTO);

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertFalse(response.getBody().success);
            assertEquals("Exemplar não encontrado!", response.getBody().message);
            assertNull(response.getBody().data);
            verify(loanService, times(1)).register(1, "12345678901");
        }

        @Test
        @DisplayName("Should return error when user not found")
        void shouldReturnErrorWhenUserNotFound() {
            when(loanService.register(anyInt(), anyString()))
                    .thenThrow(new IllegalArgumentException("Usuário não encontrado!"));

            ResponseEntity<BaseResponseDTO<LoanResponseDTO>> response = loanController.register(loanRequestDTO);

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertFalse(response.getBody().success);
            assertEquals("Usuário não encontrado!", response.getBody().message);
            verify(loanService, times(1)).register(1, "12345678901");
        }

        @Test
        @DisplayName("Should return error when user is invalid")
        void shouldReturnErrorWhenUserIsInvalid() {
            when(loanService.register(anyInt(), anyString()))
                    .thenThrow(new IllegalArgumentException("Usuário inválido"));

            ResponseEntity<BaseResponseDTO<LoanResponseDTO>> response = loanController.register(loanRequestDTO);

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertFalse(response.getBody().success);
            assertEquals("Usuário inválido", response.getBody().message);
            verify(loanService, times(1)).register(1, "12345678901");
        }

        @Test
        @DisplayName("Should return error when stock is not available")
        void shouldReturnErrorWhenStockIsNotAvailable() {
            when(loanService.register(anyInt(), anyString()))
                    .thenThrow(new IllegalArgumentException("Exemplar não disponível"));

            ResponseEntity<BaseResponseDTO<LoanResponseDTO>> response = loanController.register(loanRequestDTO);

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertFalse(response.getBody().success);
            assertEquals("Exemplar não disponível", response.getBody().message);
            verify(loanService, times(1)).register(1, "12345678901");
        }
    }

    @Nested
    @DisplayName("Devolution Tests")
    class DevolutionTests {

        @Test
        @DisplayName("Should process devolution successfully")
        void shouldProcessDevolutionSuccessfully() {
            loan.setDevolutionDate(LocalDate.now());
            when(loanService.devolution(anyInt())).thenReturn(loan);

            ResponseEntity<BaseResponseDTO<LoanResponseDTO>> response = loanController.devolution(1);

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertTrue(response.getBody().success);
            assertNotNull(response.getBody().data);
            assertNotNull(response.getBody().data.devolutionDate);
            verify(loanService, times(1)).devolution(1);
        }

        @Test
        @DisplayName("Should return error when loan not found")
        void shouldReturnErrorWhenLoanNotFound() {
            when(loanService.devolution(anyInt()))
                    .thenThrow(new IllegalArgumentException("Empréstimo não encontrado!"));

            ResponseEntity<BaseResponseDTO<LoanResponseDTO>> response = loanController.devolution(999);

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertFalse(response.getBody().success);
            assertEquals("Empréstimo não encontrado!", response.getBody().message);
            assertNull(response.getBody().data);
            verify(loanService, times(1)).devolution(999);
        }

        @Test
        @DisplayName("Should return error when devolution already completed")
        void shouldReturnErrorWhenDevolutionAlreadyCompleted() {
            when(loanService.devolution(anyInt()))
                    .thenThrow(new IllegalArgumentException("Devolução já realizada!"));

            ResponseEntity<BaseResponseDTO<LoanResponseDTO>> response = loanController.devolution(1);

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertFalse(response.getBody().success);
            assertEquals("Devolução já realizada!", response.getBody().message);
            assertNull(response.getBody().data);
            verify(loanService, times(1)).devolution(1);
        }
    }
}