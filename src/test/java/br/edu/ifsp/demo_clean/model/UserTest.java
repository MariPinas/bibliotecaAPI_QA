package br.edu.ifsp.demo_clean.model;

import static org.junit.jupiter.api.Assertions.*;
import br.edu.ifsp.demo_clean.strategy.LoanStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.mockito.Mockito.mock;

class UserTest {

    static class ConcreteUser extends User {
        public ConcreteUser(String name, String cpf, String email) {
            super(name, cpf, email);
        }
        @Override
        public LoanStrategy<?> getLoanStrategy() {
            return mock(LoanStrategy.class);
        }
    }

    private ConcreteUser user;

    private static final String DEFAULT_NAME = "Mariana Pereira";
    private static final String DEFAULT_CPF = "12345678901";
    private static final String DEFAULT_EMAIL = "mari.123@teste.com";

    @BeforeEach
    void setUp() {
        user = new ConcreteUser(DEFAULT_NAME, DEFAULT_CPF, DEFAULT_EMAIL);
    }

    @Nested
    class ConstructorAndAttributeTests {

        @Test
        @DisplayName("Should initialize with default constructor")
        void shouldInitializeWithDefaultConstructor() {
            ConcreteUser newUser = new ConcreteUser(null, null, null);
            assertNotNull(newUser);
            assertNull(newUser.getName());
            assertTrue(newUser.loans.isEmpty());
        }

        @Test
        @DisplayName("Should initialize with full constructor")
        void shouldInitializeWithFullConstructor() {
            assertEquals(DEFAULT_NAME, user.getName());
            assertEquals(DEFAULT_CPF, user.getCpf());
            assertEquals(DEFAULT_EMAIL, user.getEmail());
            assertNotNull(user.loans);
            assertTrue(user.loans.isEmpty());
        }

        @Test
        @DisplayName("Should allow name change")
        void shouldAllowNameChange() {
            String newName = "Maria Oliveira";
            user.setName(newName);
            assertEquals(newName, user.getName());
        }

        @Test
        @DisplayName("Should allow CPF change")
        void shouldAllowCpfChange() {
            String newCpf = "98765432109";
            user.setCpf(newCpf);
            assertEquals(newCpf, user.getCpf());
        }

        @Test
        @DisplayName("Should allow email change")
        void shouldAllowEmailChange() {
            String newEmail = "maria.o@teste.com";
            user.setEmail(newEmail);
            assertEquals(newEmail, user.getEmail());
        }

        @Test
        @DisplayName("Should return correct toString representation")
        void shouldReturnCorrectToString() {
            String expected = "Usuario{id=0, nome='Mariana Pereira', cpf='12345678901', email='mari.123@teste.com'}";
            assertEquals(expected, user.toString());
        }

        @Test
        @DisplayName("Should return loan strategy for concrete user")
        void shouldReturnLoanStrategyForConcreteUser() {
            assertNotNull(user.getLoanStrategy());
            assertTrue(user.getLoanStrategy() instanceof LoanStrategy);
        }
    }

    @Nested
    @DisplayName("Lógica de Empréstimos")
    class LoanLogicTests {

        private Loan loan1;
        private Loan loan2;
        private Loan loan3;
        private Stock mockStock; // Novo: precisamos de um Stock

        @BeforeEach
        void setupLoans() {
            mockStock = mock(Stock.class);
            loan1 = new Loan(
                    user,
                    mockStock,
                    LocalDate.now(),
                    LocalDate.now().plusDays(7),
                    null
            );

            loan2 = new Loan(
                    user,
                    mockStock,
                    LocalDate.now(),
                    LocalDate.now().plusDays(7),
                    null
            );


            loan3 = new Loan(
                    user,
                    mockStock,
                    LocalDate.now(),
                    LocalDate.now().plusDays(7),
                    LocalDate.now().plusDays(1)
            );
        }

        @Test
        @DisplayName("Deve retornar 0 quando não há empréstimos")
        void shouldReturnZeroWhenNoLoans() {
            assertEquals(0, user.allActiveLoans());
        }

        @Test
        @DisplayName("Deve retornar a contagem correta de empréstimos ativos")
        void shouldReturnCorrectNumberOfActiveLoans() {
            user.loans.add(loan1);
            user.loans.add(loan2);
            user.loans.add(loan3);

            assertEquals(2, user.allActiveLoans());
        }
    }
}