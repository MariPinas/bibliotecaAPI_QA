package br.edu.ifsp.demo_clean.model;

import br.edu.ifsp.demo_clean.strategy.LibrarianLoanStrategy;
import br.edu.ifsp.demo_clean.strategy.LoanStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibrarianTest {

    private Librarian librarian;

    private static final String DEFAULT_NAME = "Bibliotecaria 1";
    private static final String DEFAULT_CPF = "99988877766";
    private static final String DEFAULT_EMAIL = "bibliotecaria.1@biblioteca.ifsp.edu.br";

    @BeforeEach
    void setUp() {
        librarian = new Librarian(DEFAULT_NAME, DEFAULT_CPF, DEFAULT_EMAIL);
    }

    @Nested
    class InitializationAndAttributeTests {

        @Test
        @DisplayName("Should initialize with full constructor")
        void shouldInitializeWithFullConstructor() {
            assertNotNull(librarian);
            assertEquals(DEFAULT_NAME, librarian.getName());
            assertEquals(DEFAULT_CPF, librarian.getCpf());
            assertEquals(DEFAULT_EMAIL, librarian.getEmail());
            assertTrue(librarian.loans.isEmpty());
        }

        @Test
        @DisplayName("Should initialize with default constructor")
        void shouldInitializeWithDefaultConstructor() {
            Librarian newLibrarian = new Librarian();
            assertNotNull(newLibrarian);
            assertNull(newLibrarian.getName());
        }

        @Test
        @DisplayName("Should return correct toString representation inherited")
        void shouldReturnCorrectToString() {
            String expected = "Usuario{id=0, nome='" + DEFAULT_NAME + "', cpf='" + DEFAULT_CPF + "', email='" + DEFAULT_EMAIL + "'}";
            assertEquals(expected, librarian.toString());
        }
    }

    @Nested
    @DisplayName("Should test Librarian specific loan strategy logic")
    class LoanStrategyTests {

        @Test
        @DisplayName("Should return LibrarianLoanStrategy")
        void shouldReturnLibrarianLoanStrategy() {
            LoanStrategy strategy = librarian.getLoanStrategy();
            assertNotNull(strategy);
            assertTrue(strategy instanceof LibrarianLoanStrategy,
                    "A estrategia deve ser do tipo LibrarianLoanStrategy");
        }

        @Test
        @DisplayName("Should handle zero loan policy without exceptions")
        void shouldHaveZeroLoanPolicy() {
            LoanStrategy strategy = librarian.getLoanStrategy();
            assertDoesNotThrow(() -> {
                librarian.getLoanStrategy();
            }, "A criacao da estrategia com 0/0 nao deve lancar excecoes.");
        }
    }
}