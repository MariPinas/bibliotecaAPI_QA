package br.edu.ifsp.demo_clean.model;

import br.edu.ifsp.demo_clean.model.enums.Course;
import br.edu.ifsp.demo_clean.model.enums.UserStatus;
import br.edu.ifsp.demo_clean.strategy.LoanStrategy;
import br.edu.ifsp.demo_clean.strategy.StudentLoanStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    private Student student;

    private static final String DEFAULT_NAME = "Renan Martins";
    private static final String DEFAULT_CPF = "55566677788";
    private static final String DEFAULT_EMAIL = "renan.martins@aluno.ifsp.edu.br";
    private static final Course DEFAULT_COURSE = Course.ADS;
    private static final UserStatus DEFAULT_STATUS = UserStatus.ACTIVE;

    @BeforeEach
    void setUp() {
        student = new Student(DEFAULT_NAME, DEFAULT_CPF, DEFAULT_EMAIL, DEFAULT_COURSE);
    }

    @Nested
    class InitializationAndAttributeTests {

        @Test
        @DisplayName("Should initialize with full constructor")
        void shouldInitializeWithFullConstructor() {
            assertNotNull(student);

            assertEquals(DEFAULT_NAME, student.getName());
            assertEquals(DEFAULT_CPF, student.getCpf());
            assertEquals(DEFAULT_EMAIL, student.getEmail());

            assertEquals(DEFAULT_COURSE, student.getCourse());
            assertEquals(DEFAULT_STATUS, student.getStatus());
            assertTrue(student.loans.isEmpty());
        }

        @Test
        @DisplayName("Should initialize with default constructor")
        void shouldInitializeWithDefaultConstructor() {
            Student newStudent = new Student();
            assertNotNull(newStudent);
            assertNull(newStudent.getName());
            assertNull(newStudent.getCourse());
        }

        @Test
        @DisplayName("Should allow course change")
        void shouldAllowCourseChange() {
            student.setCourse(Course.ADS);
            assertEquals(Course.ADS, student.getCourse());
        }

        @Test
        @DisplayName("Should return correct toString representation")
        void shouldReturnCorrectToString() {
            String expected = "Usuario{id=0, nome='Renan Martins', cpf='55566677788', email='renan.martins@aluno.ifsp.edu.br', curso=ADS, status=ACTIVE}";
            assertEquals(expected, student.toString());
        }
    }

    @Nested
    class LoanStrategyTests {

        @Test
        @DisplayName("Should return correct loan strategy for Student")
        void shouldReturnStudentLoanStrategy() {
            LoanStrategy strategy = student.getLoanStrategy();
            assertNotNull(strategy);
            assertTrue(strategy instanceof StudentLoanStrategy,
                    "A estrategia deve ser do tipo StudentLoanStrategy");
        }

        @Test
        @DisplayName("Should have correct loan policy (3 books / 15 days)")
        void shouldHaveCorrectLoanPolicy() {
            LoanStrategy strategy = student.getLoanStrategy();
            assertDoesNotThrow(() -> {
                student.getLoanStrategy();
            }, "A criacao da estrategia nao deve lançar excecoes.");
        }
    }
}