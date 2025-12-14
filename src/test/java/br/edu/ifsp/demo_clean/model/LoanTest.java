package br.edu.ifsp.demo_clean.model;

import br.edu.ifsp.demo_clean.strategy.LoanStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LoanTest {
    static class ConcreteUser extends User {
        public ConcreteUser() {
            super("Test User", "12345678900", "test@gmail.com");
        }

        @Override
        public LoanStrategy getLoanStrategy() {
            return new LoanStrategy() {
                @Override
                public int calculateLoanTermInDays(User user, Book book) {
                    return 7;
                }

                @Override
                public int getBookLimit() {
                    return 3;
                }
            };
        }
    }

    private ConcreteUser testUser;
    private Stock testStock;

    @BeforeEach
    void setUp() {
        testUser = new ConcreteUser();
        testStock = new Stock();
    }

    @Nested
    class ConstructorTests {
        @Test
        void defaultConstructorShouldCreateNonNullObject() {
            Loan loan = new Loan();
            assertNotNull(loan);
        }

        @Test
        void fullConstructorShouldSetAllFieldsCorrectly() {
            LocalDate loanDate = LocalDate.of(2025, 10, 1);
            LocalDate dueDate = LocalDate.of(2025, 10, 15);
            LocalDate devolutionDate = LocalDate.of(2025, 10, 14);

            Loan loan = new Loan(testUser, testStock, loanDate, dueDate, devolutionDate);

            assertEquals(testUser, loan.getUser());
            assertEquals(testStock, loan.getStock());
            assertEquals(loanDate, loan.getLoanDate());
            assertEquals(dueDate, loan.getDueDate());
            assertEquals(devolutionDate, loan.getDevolutionDate());
        }
    }

    @Nested
    class GetterAndSetterTests {
        private Loan loan;

        @BeforeEach
        void setup() {
            loan = new Loan();
        }

        @Test
        void idGetterAndSetterTest() {
            loan.setId(10);
            assertEquals(10, loan.getId());
        }

        @Test
        void userGetterAndSetterTest() {
            loan.setUser(testUser);
            assertEquals(testUser, loan.getUser());
        }

        @Test
        void stockGetterAndSetterTest() {
            loan.setStock(testStock);
            assertEquals(testStock, loan.getStock());
        }

        @Test
        void loanDateGetterAndSetterTest() {
            LocalDate date = LocalDate.of(2025, 11, 5);
            loan.setLoanDate(date);
            assertEquals(date, loan.getLoanDate());
        }

        @Test
        void dueDateGetterAndSetterTest() {
            LocalDate date = LocalDate.of(2025, 11, 20);
            loan.setDueDate(date);
            assertEquals(date, loan.getDueDate());
        }

        @Test
        void devolutionDateGetterAndSetterTest() {
            LocalDate date = LocalDate.of(2025, 11, 18);
            loan.setDevolutionDate(date);
            assertEquals(date, loan.getDevolutionDate());
        }
    }

    @Nested
    class StatusTests {
        private Loan loan;

        @BeforeEach
        void setup() {
            LocalDate pastDate = LocalDate.now().minusDays(5);
            loan = new Loan(testUser, testStock, pastDate.minusDays(10), pastDate, null);
        }

        @Nested
        class IsCompletedTests {
            @Test
            void shouldReturnTrueWhenDevolutionDateIsNotNull() {
                loan.setDevolutionDate(LocalDate.now());
                assertTrue(loan.isCompleted());
            }

            @Test
            void shouldReturnFalseWhenDevolutionDateIsNull() {
                loan.setDevolutionDate(null);
                assertFalse(loan.isCompleted());
            }
        }

        @Nested
        class IsOverdueTests {
            @Test
            void shouldReturnTrueWhenDueDateIsInThePast() {
                assertTrue(loan.isOverdue());
            }

            @Test
            void shouldReturnFalseWhenDueDateIsInTheFuture() {
                LocalDate futureDate = LocalDate.now().plusDays(5);
                loan.setDueDate(futureDate);
                assertFalse(loan.isOverdue());
            }

            @Test
            void shouldReturnFalseWhenDueDateIsToday() {
                LocalDate today = LocalDate.now();
                loan.setDueDate(today);
                assertFalse(loan.isOverdue());
            }
        }
    }
}