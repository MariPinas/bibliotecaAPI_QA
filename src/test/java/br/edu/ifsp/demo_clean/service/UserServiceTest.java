package br.edu.ifsp.demo_clean.service;

import br.edu.ifsp.demo_clean.dto.UserDTO;
import br.edu.ifsp.demo_clean.factory.UserRegistry;
import br.edu.ifsp.demo_clean.model.*;

import br.edu.ifsp.demo_clean.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    private static final String VALID_CPF = "78290777035";
    private static final String NON_EXISTENT_CPF = "99999999999";
    private static final String EXISTING_CPF = "08928682010";

    private UserDTO baseDto;
    private Student mockStudent;
    private Professor mockProfessor;

    @BeforeEach
    void setUp() {
        baseDto = new UserDTO();
        baseDto.cpf = VALID_CPF;
        baseDto.name = "Novo Usuário";
        baseDto.email = "novo@teste.com";

        mockStudent = mock(Student.class);
        mockProfessor = mock(Professor.class);
    }

    @Nested
    class AddUserTests {
        private Professor mockCreatedProfessor;

        @BeforeEach
        void setupAddUser() {
            mockCreatedProfessor = mock(Professor.class);
        }

        @Test
        @DisplayName("Should add user professor successfully when CPF is unique")
        void shouldAddUserSuccessfully() {
            when(userRepository.findByCpf(VALID_CPF)).thenReturn(Optional.empty());
            when(userRepository.save(any(Professor.class))).thenReturn(mockCreatedProfessor);

            try (var mockedUserRegistry = mockStatic(UserRegistry.class)) {
                when(UserRegistry.create(any(UserDTO.class), eq(Professor.class)))
                        .thenReturn(mock(Professor.class));

                User createdUser = userService.addUser(baseDto, Professor.class);

                assertEquals(mockCreatedProfessor, createdUser);
                verify(userRepository, times(1)).findByCpf(VALID_CPF);
                verify(userRepository, times(1)).save(any(Professor.class));
            } catch (Exception e) {
                fail("Nao deveria ter lançado excecao: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("Should fail to add user if CPF already exists")
        void shouldFailIfCpfAlreadyExists() {
            when(userRepository.findByCpf(VALID_CPF)).thenReturn(Optional.of(mockStudent));

            Error error = assertThrows(Error.class, () -> {
                userService.addUser(baseDto, Professor.class);
            });

            assertTrue(error.getMessage().contains("já cadastrado"));
            verify(userRepository, never()).save(any());
        }

    }

    @Nested
    class GetUserTests {

        @Test
        @DisplayName("Should return user when found by CPF")
        void shouldThrowErrorWhenUserNotFound() {
            when(userRepository.findByCpf(NON_EXISTENT_CPF)).thenReturn(Optional.empty());

            Error error = assertThrows(Error.class, () -> {
                userService.getUser(NON_EXISTENT_CPF);
            });

            assertTrue(error.getMessage().contains("não encontrado"), "A mensagem de erro deve conter 'não encontrado'.");
            verify(userRepository, times(1)).findByCpf(NON_EXISTENT_CPF);
        }
    }

    @Nested
    class UpdateUserTests {
        private UserDTO updateDto;

        @BeforeEach
        void setupUpdate() {
            updateDto = new UserDTO();
        }

        @Test
        @DisplayName("Should update name and email on student")
        void shouldUpdateNameAndEmailOnStudent() {
            when(userRepository.findByCpf(EXISTING_CPF)).thenReturn(Optional.of(mockStudent));
            when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0)); // Retorna o mesmo mock

            updateDto.name = "Novo Nome";
            updateDto.email = "novo@email.com";

            userService.updateUser(updateDto, EXISTING_CPF);
        }

    }

    @Nested
    class DeleteUserTests {

        @Test
        @DisplayName("Should delete user if no active loans")
        void shouldDeleteUserIfNoActiveLoans() {
            when(userRepository.findByCpf(EXISTING_CPF)).thenReturn(Optional.of(mockStudent));
            when(mockStudent.allActiveLoans()).thenReturn(0);

            userService.deleteUser(EXISTING_CPF);

            verify(userRepository, times(1)).delete(mockStudent);
        }

        @Test
        @DisplayName("Should throw error if user has active loans")
        void shouldThrowErrorIfUserHasActiveLoans() {
            when(userRepository.findByCpf(EXISTING_CPF)).thenReturn(Optional.of(mockStudent));
            when(mockStudent.allActiveLoans()).thenReturn(2);
            Error error = assertThrows(Error.class, () -> {
                userService.deleteUser(EXISTING_CPF);
            });

            assertTrue(error.getMessage().contains("empréstimos ativos"), "A mensagem de erro deve indicar empréstimos ativos.");
            verify(userRepository, never()).delete(any());
        }

    }
}