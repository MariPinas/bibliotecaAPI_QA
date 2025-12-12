package br.edu.ifsp.demo_clean.service;

import br.edu.ifsp.demo_clean.model.Loan;
import br.edu.ifsp.demo_clean.model.User;
import br.edu.ifsp.demo_clean.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService - Functional and Validation Tests")
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Mock
    private User validUser;

    private static final String VALID_CPF = "89429659030";
    private static final String NON_EXISTENT_CPF = "46545446576";
    private static final int TEST_ID = 1;

    @Nested
    @DisplayName("CPF Validation Tests (checkCpf) in addUser")
    class CheckCpfTests {

        @Test
        @DisplayName("Should pass with valid and available CPF")
        void checkCpf_ShouldPassForValidAndAvailableCpf() {
            when(validUser.getCpf()).thenReturn(VALID_CPF);

            when(userRepository.findByCpf(VALID_CPF)).thenReturn(Optional.empty());
            when(userRepository.save(any(User.class))).thenReturn(validUser);


            assertDoesNotThrow(() -> userService.addUser(validUser));


            verify(userRepository, times(1)).save(validUser);
        }

        @Test
        @DisplayName("Should fail if CPF is null or invalid (format)")
        void checkCpf_ShouldThrowException_WhenCpfIsNull() {
            when(validUser.getCpf()).thenReturn(null);
            assertThrows(IllegalArgumentException.class, () -> userService.addUser(validUser));

            when(validUser.getCpf()).thenReturn("123");
            assertThrows(IllegalArgumentException.class, () -> userService.addUser(validUser));
        }

        @Test
        @DisplayName("Should fail if CPF is sequential (e.g., 11111111111)")
        void checkCpf_ShouldThrowException_WhenCpfIsSequential() {
            when(validUser.getCpf()).thenReturn("11111111111");

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> userService.addUser(validUser)
            );
            assertTrue(exception.getMessage().contains("sequência repetida"));
        }

        @Test
        @DisplayName("Should fail if the first verification digit is incorrect")
        void checkCpf_ShouldThrowException_WhenFirstVerifierDigitIsIncorrect() {

            when(validUser.getCpf()).thenReturn("12345678919");

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> userService.addUser(validUser)
            );

            assertTrue(exception.getMessage().contains("primeiro dígito verificador incorreto"));
        }

        @Test
        @DisplayName("Should fail if the CPF is already registered")
        void checkCpf_ShouldThrowException_WhenCpfIsAlreadyRegistered() {

            when(validUser.getCpf()).thenReturn(VALID_CPF);

            when(userRepository.findByCpf(VALID_CPF)).thenReturn(Optional.of(mock(User.class)));

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> userService.addUser(validUser)
            );

            assertTrue(exception.getMessage().contains("já cadastrado"));
        }
    }

    @Nested
    @DisplayName("Retrieval Tests (getUser and getUsers)")
    class GetUsersTests {

        @Test
        @DisplayName("getUser: Should find and return an existing user")
        void getUser_ShouldReturnUser_WhenUserExists() {
            when(userRepository.findByCpf(VALID_CPF)).thenReturn(Optional.of(validUser));

            User result = userService.getUser(VALID_CPF);

            assertNotNull(result);
            assertEquals(validUser.getId(), result.getId());
        }

        @Test
        @DisplayName("getUser: Should fail when searching for a non-existent user")
        void getUser_ShouldThrowException_WhenUserDoesNotExist() {
            when(userRepository.findByCpf(NON_EXISTENT_CPF)).thenReturn(Optional.empty());

            assertThrows(IllegalArgumentException.class,
                    () -> userService.getUser(NON_EXISTENT_CPF)
            );
        }

        @Test
        @DisplayName("getUsers: Should return all users")
        void getUsers_ShouldReturnAllUsers() {
            List<User> expectedList = List.of(validUser, mock(User.class));
            when(userRepository.findAll()).thenReturn(expectedList);

            List<User> resultList = userService.getUsers();

            assertNotNull(resultList);
            assertEquals(2, resultList.size());
            verify(userRepository, times(1)).findAll();
        }
    }

    @Nested
    @DisplayName("UpdateUser Method Tests")
    class UpdateUserTests {

        @Test
        @DisplayName("Should update and save user with the original ID")
        void updateUser_ShouldSetIdAndSave() {
            User existingUser = mock(User.class);
            when(existingUser.getId()).thenReturn(10);

            User userFromDTO = mock(User.class);

            when(userRepository.findByCpf(VALID_CPF)).thenReturn(Optional.of(existingUser));
            when(userRepository.save(any(User.class))).thenReturn(userFromDTO);

            User result = userService.updateUser(userFromDTO, VALID_CPF);

            assertNotNull(result);
            verify(userFromDTO).setId(10);
            verify(userRepository, times(1)).save(userFromDTO);
        }

        @Test
        @DisplayName("Should fail when trying to update a non-existent user")
        void updateUser_ShouldThrowException_WhenUserDoesNotExist() {
            when(userRepository.findByCpf(NON_EXISTENT_CPF)).thenReturn(Optional.empty());

            assertThrows(IllegalArgumentException.class,
                    () -> userService.updateUser(mock(User.class), NON_EXISTENT_CPF)
            );
        }
    }

    @Nested
    @DisplayName("DeleteUser Method Tests")
    class DeleteUserTests {

        @Test
        @DisplayName("Should delete a user without active loans")
        void deleteUser_ShouldDeleteUser_WhenNoActiveLoans() {
            when(userRepository.findByCpf(VALID_CPF)).thenReturn(Optional.of(validUser));

            User result = userService.deleteUser(VALID_CPF);

            assertNotNull(result);
            verify(userRepository, times(1)).delete(validUser);
        }

        @Test
        @DisplayName("Should fail to delete user with active loans")
        void deleteUser_ShouldThrowException_WhenActiveLoansExist() {
            when(userRepository.findByCpf(VALID_CPF)).thenReturn(Optional.of(validUser));

            Loan activeLoan = mock(Loan.class);
            when(validUser.getAllActiveLoans()).thenReturn(List.of(activeLoan));

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> userService.deleteUser(VALID_CPF)
            );
            assertTrue(exception.getMessage().contains("possui empréstimos ativos"));
            verify(userRepository, never()).delete(any(User.class));
        }

        @Test
        @DisplayName("Should fail when trying to delete a non-existent user")
        void deleteUser_ShouldThrowException_WhenUserDoesNotExist() {
            when(userRepository.findByCpf(NON_EXISTENT_CPF)).thenReturn(Optional.empty());

            assertThrows(IllegalArgumentException.class,
                    () -> userService.deleteUser(NON_EXISTENT_CPF)
            );
            verify(userRepository, never()).delete(any(User.class));
        }
    }
}