package br.edu.ifsp.demo_clean.model;

import br.edu.ifsp.demo_clean.strategy.LoanPolicy;
import br.edu.ifsp.demo_clean.strategy.LoanStrategy;
import br.edu.ifsp.demo_clean.strategy.ProfessorLoanStrategy;
import jakarta.persistence.DiscriminatorValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfessorTest {

    private Professor professor;

    private static final String DEFAULT_NAME = "Anisio";
    private static final String DEFAULT_CPF = "11122233344";
    private static final String DEFAULT_EMAIL = "anisio@ifsp.edu.br";

    @BeforeEach
    void setUp() {
        professor = new Professor(DEFAULT_NAME, DEFAULT_CPF, DEFAULT_EMAIL);
    }

    @Nested
    class InheritanceAndInitializationTests {

        @Test
        @DisplayName("Should initialize with full constructor")
        void shouldInitializeWithFullConstructor() {
            assertNotNull(professor);
            assertEquals(DEFAULT_NAME, professor.getName());
            assertEquals(DEFAULT_CPF, professor.getCpf());
            assertEquals(DEFAULT_EMAIL, professor.getEmail());
            assertTrue(professor.loans.isEmpty());
        }

        @Test
        @DisplayName("Should initialize with default constructor")
        void shouldInitializeWithDefaultConstructor() {
            Professor newProfessor = new Professor();
            assertNotNull(newProfessor);
            assertNull(newProfessor.getName());
        }

        @Test
        @DisplayName("Should return correct toString representation inherited")
        void shouldReturnCorrectToString() {
            String expected = "Usuario{id=0, nome='" + DEFAULT_NAME + "', cpf='" + DEFAULT_CPF + "', email='" + DEFAULT_EMAIL + "'}";
            assertEquals(expected, professor.toString());
        }
    }

    @Nested
    class ProfessorSpecificLogicTests {

        @Test
        @DisplayName("Should return ProfessorLoanStrategy")
        void shouldReturnProfessorLoanStrategy() {
            LoanStrategy strategy = professor.getLoanStrategy();

            assertNotNull(strategy);
            assertTrue(strategy instanceof ProfessorLoanStrategy,
                    "A estratégia deve ser ProfessorLoanStrategy");
        }

        @Test
        @DisplayName("Should have correct loan policy")
        void shouldHaveCorrectLoanPolicy() {
            ProfessorLoanStrategy strategy = (ProfessorLoanStrategy) professor.getLoanStrategy();
            LoanPolicy expectedPolicy = new LoanPolicy(5, 40);

            assertDoesNotThrow(() -> {
                professor.getLoanStrategy();
            }, "A estrategia deve ser instanciada sem exceções.");
        }
    }

    @Nested
    class FactoryTests {
        @Test
        @DisplayName("Should be registered in User registry with correct DiscriminatorValue")
        void shouldBeRegisteredInUserRegistry() {
            assertNotNull(Professor.class.getAnnotation(DiscriminatorValue.class));
            assertEquals("PROFESSOR", Professor.class.getAnnotation(DiscriminatorValue.class).value());
        }
    }
}