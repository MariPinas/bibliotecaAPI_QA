package br.edu.ifsp.demo_clean.service;

import br.edu.ifsp.demo_clean.model.*;
import br.edu.ifsp.demo_clean.model.enums.BookCategory;
import br.edu.ifsp.demo_clean.model.enums.Course;
import br.edu.ifsp.demo_clean.model.enums.UserStatus;
import br.edu.ifsp.demo_clean.repository.LoanRepository;
import br.edu.ifsp.demo_clean.repository.StockRepository;
import br.edu.ifsp.demo_clean.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("LoanService Tests")
class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private LoanService loanService;

    private Student student;
    private Professor professor;
    private Book book;
    private Book relatedBook;
    private Stock stock;
    private Loan loan;

    @BeforeEach
    void setUp() {
        book = new Book(123456, "Clean Code", "Robert Martin", "Seila oque", "1", BookCategory.COMPUTING);
        relatedBook = new Book(789012, "Programacao", "Author", "Teste", "2", BookCategory.COMPUTING);
        stock = new Stock(1, true, book);
        student = new Student("Mariana", "12345678901", "mariana@gmail.com", Course.ADS);
        professor = new Professor("Anisio", "98765432100", "anisio@gmail.com");
        loan = new Loan(student, stock, LocalDate.now(), LocalDate.now().plusDays(15), null);
    }

    @Nested
    @DisplayName("Register Loan Tests")
    class RegisterLoanTests {

        @Test
        @DisplayName("Should register loan successfully for student")
        void shouldRegisterLoanSuccessfullyForStudent() {
            when(stockRepository.findById(1)).thenReturn(Optional.of(stock));
            when(userRepository.findByCpf("12345678901")).thenReturn(Optional.of(student));
            when(loanRepository.save(any(Loan.class))).thenReturn(loan);

            Loan result = loanService.register(1, "12345678901");

            assertNotNull(result);
            assertEquals(student, result.getUser());
            assertEquals(stock, result.getStock());
            assertNotNull(result.getLoanDate());
            assertNotNull(result.getDueDate());
            assertNull(result.getDevolutionDate());

            ArgumentCaptor<Stock> stockCaptor = ArgumentCaptor.forClass(Stock.class);
            verify(stockRepository).save(stockCaptor.capture());
            assertFalse(stockCaptor.getValue().getAvailability());
            verify(loanRepository).save(any(Loan.class));
        }

        @Test
        @DisplayName("Should register loan with extended deadline for related course")
        void shouldRegisterLoanWithExtendedDeadlineForRelatedCourse() {
            Stock relatedStock = new Stock(2, true, relatedBook);
            when(stockRepository.findById(2)).thenReturn(Optional.of(relatedStock));
            when(userRepository.findByCpf("12345678901")).thenReturn(Optional.of(student));
            when(loanRepository.save(any(Loan.class))).thenAnswer(invocation -> {
                Loan savedLoan = invocation.getArgument(0);
                return savedLoan;
            });

            Loan result = loanService.register(2, "12345678901");

            assertNotNull(result);
            long daysBetween = ChronoUnit.DAYS.between(result.getLoanDate(), result.getDueDate());
            assertEquals(30, daysBetween);
            verify(loanRepository).save(any(Loan.class));
        }

        @Test
        @DisplayName("Should register loan successfully for professor")
        void shouldRegisterLoanSuccessfullyForProfessor() {
            when(stockRepository.findById(1)).thenReturn(Optional.of(stock));
            when(userRepository.findByCpf("98765432100")).thenReturn(Optional.of(professor));
            when(loanRepository.save(any(Loan.class))).thenAnswer(invocation -> invocation.getArgument(0));

            Loan result = loanService.register(1, "98765432100");

            assertNotNull(result);
            assertEquals(professor, result.getUser());
            long daysBetween = ChronoUnit.DAYS.between(result.getLoanDate(), result.getDueDate());
            assertEquals(40, daysBetween);
            verify(loanRepository).save(any(Loan.class));
        }

        @Test
        @DisplayName("Should throw exception when stock not found")
        void shouldThrowExceptionWhenStockNotFound() {
            when(stockRepository.findById(999)).thenReturn(Optional.empty());

            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> loanService.register(999, "12345678901")
            );

            assertEquals("Exemplar não encontrado!", exception.getMessage());
            verify(stockRepository).findById(999);
            verify(userRepository, never()).findByCpf(anyString());
            verify(loanRepository, never()).save(any());
        }

        @Test
        @DisplayName("Should throw exception when user not found")
        void shouldThrowExceptionWhenUserNotFound() {
            when(stockRepository.findById(1)).thenReturn(Optional.of(stock));
            when(userRepository.findByCpf("99999999999")).thenReturn(Optional.empty());

            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> loanService.register(1, "99999999999")
            );

            assertEquals("Usuário não encontrado!", exception.getMessage());
            verify(stockRepository).findById(1);
            verify(userRepository).findByCpf("99999999999");
            verify(loanRepository, never()).save(any());
        }

        @Test
        @DisplayName("Should throw exception when user has reached book limit")
        void shouldThrowExceptionWhenUserHasReachedBookLimit() {
            List<Loan> activeLoans = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                Stock s = new Stock(i, false, book);
                Loan l = new Loan(student, s, LocalDate.now(), LocalDate.now().plusDays(15), null);
                activeLoans.add(l);
            }
            student.loans = activeLoans;

            when(stockRepository.findById(1)).thenReturn(Optional.of(stock));
            when(userRepository.findByCpf("12345678901")).thenReturn(Optional.of(student));

            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> loanService.register(1, "12345678901")
            );

            assertEquals("Usuário inválido", exception.getMessage());
            verify(loanRepository, never()).save(any());
        }

        @Test
        @DisplayName("Should throw exception when user is suspended")
        void shouldThrowExceptionWhenUserIsSuspended() {
            List<Loan> overdueLoans = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                Stock s = new Stock(i, false, book);
                Loan l = new Loan(student, s, LocalDate.now().minusDays(30), LocalDate.now().minusDays(15), null);
                overdueLoans.add(l);
            }
            student.loans = overdueLoans;

            when(stockRepository.findById(1)).thenReturn(Optional.of(stock));
            when(userRepository.findByCpf("12345678901")).thenReturn(Optional.of(student));

            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> loanService.register(1, "12345678901")
            );

            assertEquals("Usuário inválido", exception.getMessage());
            assertEquals(UserStatus.SUSPENDED, student.getStatus());
            verify(loanRepository, never()).save(any());
        }

        @Test
        @DisplayName("Should throw exception when user is inactive")
        void shouldThrowExceptionWhenUserIsInactive() {
            List<Loan> overdueLoans = new ArrayList<>();
            Stock s = new Stock(10, false, book);
            Loan l = new Loan(student, s, LocalDate.now().minusDays(30), LocalDate.now().minusDays(15), null);
            overdueLoans.add(l);
            student.loans = overdueLoans;

            when(stockRepository.findById(1)).thenReturn(Optional.of(stock));
            when(userRepository.findByCpf("12345678901")).thenReturn(Optional.of(student));

            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> loanService.register(1, "12345678901")
            );

            assertEquals("Usuário inválido", exception.getMessage());
            assertEquals(UserStatus.INACTIVE, student.getStatus());
            verify(loanRepository, never()).save(any());
        }

        @Test
        @DisplayName("Should throw exception when stock is not available")
        void shouldThrowExceptionWhenStockIsNotAvailable() {
            stock.setAvailability(false);
            when(stockRepository.findById(1)).thenReturn(Optional.of(stock));
            when(userRepository.findByCpf("12345678901")).thenReturn(Optional.of(student));

            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> loanService.register(1, "12345678901")
            );

            assertEquals("Exemplar não disponível", exception.getMessage());
            verify(loanRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("Devolution Tests")
    class DevolutionTests {

        @Test
        @DisplayName("Should process devolution successfully")
        void shouldProcessDevolutionSuccessfully() {
            when(loanRepository.findById(1)).thenReturn(Optional.of(loan));
            when(loanRepository.save(any(Loan.class))).thenReturn(loan);

            Loan result = loanService.devolution(1);

            assertNotNull(result);
            assertNotNull(result.getDevolutionDate());
            assertEquals(LocalDate.now(), result.getDevolutionDate());
            assertTrue(result.getStock().getAvailability());

            verify(loanRepository).findById(1);
            verify(loanRepository).save(loan);
            verify(stockRepository).save(stock);
        }

        @Test
        @DisplayName("Should throw exception when loan not found")
        void shouldThrowExceptionWhenLoanNotFound() {
            when(loanRepository.findById(999)).thenReturn(Optional.empty());

            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> loanService.devolution(999)
            );

            assertEquals("Empréstimo não encontrado!", exception.getMessage());
            verify(loanRepository).findById(999);
            verify(loanRepository, never()).save(any());
            verify(stockRepository, never()).save(any());
        }

        @Test
        @DisplayName("Should throw exception when devolution already completed")
        void shouldThrowExceptionWhenDevolutionAlreadyCompleted() {
            loan.setDevolutionDate(LocalDate.now().minusDays(1));
            when(loanRepository.findById(1)).thenReturn(Optional.of(loan));

            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> loanService.devolution(1)
            );

            assertEquals("Devolução já realizada!", exception.getMessage());
            verify(loanRepository).findById(1);
            verify(loanRepository, never()).save(any());
            verify(stockRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("List Operations Tests")
    class ListOperationsTests {

        @Test
        @DisplayName("Should list all active assets")
        void shouldListAllActiveAssets() {
            List<Loan> activeLoans = List.of(loan);
            when(loanRepository.findAllByDevolutionDateIsNull()).thenReturn(activeLoans);

            List<Loan> result = loanService.listAssets();

            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(loan, result.get(0));
            verify(loanRepository).findAllByDevolutionDateIsNull();
        }

        @Test
        @DisplayName("Should return empty list when no active assets")
        void shouldReturnEmptyListWhenNoActiveAssets() {
            when(loanRepository.findAllByDevolutionDateIsNull()).thenReturn(new ArrayList<>());

            List<Loan> result = loanService.listAssets();

            assertNotNull(result);
            assertTrue(result.isEmpty());
            verify(loanRepository).findAllByDevolutionDateIsNull();
        }

        @Test
        @DisplayName("Should list all loan history")
        void shouldListAllLoanHistory() {
            Loan completedLoan = new Loan(student, stock, LocalDate.now().minusDays(20),
                    LocalDate.now().minusDays(5), LocalDate.now().minusDays(3));
            List<Loan> allLoans = List.of(loan, completedLoan);
            when(loanRepository.findAll()).thenReturn(allLoans);

            List<Loan> result = loanService.listHistory();

            assertNotNull(result);
            assertEquals(2, result.size());
            verify(loanRepository).findAll();
        }

        @Test
        @DisplayName("Should return empty list when no loan history")
        void shouldReturnEmptyListWhenNoLoanHistory() {
            when(loanRepository.findAll()).thenReturn(new ArrayList<>());

            List<Loan> result = loanService.listHistory();

            assertNotNull(result);
            assertTrue(result.isEmpty());
            verify(loanRepository).findAll();
        }
    }
}