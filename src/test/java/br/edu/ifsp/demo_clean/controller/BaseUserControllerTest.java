package br.edu.ifsp.demo_clean.controller;
import br.edu.ifsp.demo_clean.dto.UserDTO;
import br.edu.ifsp.demo_clean.model.Professor;
import br.edu.ifsp.demo_clean.model.User;
import br.edu.ifsp.demo_clean.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BaseUserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private ConcreteUserController controller;
    private static class ConcreteUserController extends BaseUserController {
        public ConcreteUserController(UserService userService) {
            super(userService);
        }

        @Override
        protected Class<? extends User> getUserClass() {
            return Professor.class;
        }
    }
    private static final String MOCK_CPF = "12345678900";
    private UserDTO baseDto;
    private Professor mockUser;

    @BeforeEach
    void setUp() {
        baseDto = new UserDTO();
        baseDto.cpf = MOCK_CPF;
        baseDto.name = "Teste User";

        mockUser = mock(Professor.class);
    }


    @Test
    @DisplayName("POST / - Should return 200 OK and created user")
    void addUser_shouldReturnOkAndCreatedUser() {
        when(userService.addUser(baseDto, Professor.class)).thenReturn(mockUser);

        ResponseEntity<?> response = controller.addUser(baseDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUser, response.getBody());
        verify(userService, times(1)).addUser(baseDto, Professor.class);
    }

    @Test
    @DisplayName("POST / - Should return 500 INTERNAL_SERVER_ERROR on error")
    void addUser_shouldReturnInternalServerErrorOnError() {
        String errorMessage = "CPF já cadastrado.";
        when(userService.addUser(baseDto, Professor.class)).thenThrow(new Error(errorMessage));

        ResponseEntity<?> response = controller.addUser(baseDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
        verify(userService, times(1)).addUser(baseDto, Professor.class);
    }


    @Test
    @DisplayName("GET /{cpf} - Should return 200 OK and found user")
    void getUser_shouldReturnOkAndFoundUser() {
        when(userService.getUser(MOCK_CPF)).thenReturn(mockUser);

        ResponseEntity<User> response = controller.getUser(MOCK_CPF);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUser, response.getBody());
        verify(userService, times(1)).getUser(MOCK_CPF);
    }

    @Test
    @DisplayName("GET /{cpf} - Should return 500 INTERNAL_SERVER_ERROR on error")
    void getUser_shouldReturnInternalServerErrorOnError() {
        when(userService.getUser(MOCK_CPF)).thenThrow(new Error("Usuário não encontrado."));

        ResponseEntity<User> response = controller.getUser(MOCK_CPF);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(null, response.getBody());
        verify(userService, times(1)).getUser(MOCK_CPF);
    }


    @Test
    @DisplayName("PUT /{cpf} - Should return 200 OK and updated user")
    void updateUser_shouldReturnOkAndUpdatedUser() {
        when(userService.updateUser(baseDto, MOCK_CPF)).thenReturn(mockUser);

        ResponseEntity<User> response = controller.updateUser(baseDto, MOCK_CPF);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUser, response.getBody());
        verify(userService, times(1)).updateUser(baseDto, MOCK_CPF);
    }

    @Test
    @DisplayName("PUT /{cpf} - Should return 500 INTERNAL_SERVER_ERROR on error")
    void updateUser_shouldReturnInternalServerErrorOnError() {
        when(userService.updateUser(baseDto, MOCK_CPF)).thenThrow(new Error("Erro de validação."));

        ResponseEntity<User> response = controller.updateUser(baseDto, MOCK_CPF);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(null, response.getBody());
        verify(userService, times(1)).updateUser(baseDto, MOCK_CPF);
    }


    @Test
    @DisplayName("DELETE /{cpf} - Should return 200 OK and deleted user")
    void deleteUser_shouldReturnOkAndDeletedUser() {
        when(userService.deleteUser(MOCK_CPF)).thenReturn(mockUser);

        ResponseEntity<User> response = controller.deleteUser(MOCK_CPF);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUser, response.getBody());
        verify(userService, times(1)).deleteUser(MOCK_CPF);
    }

    @Test
    @DisplayName("DELETE /{cpf} - Should return 500 INTERNAL_SERVER_ERROR on error")
    void deleteUser_shouldReturnInternalServerErrorOnError() {
        when(userService.deleteUser(MOCK_CPF)).thenThrow(new Error("Usuário possui empréstimos."));

        ResponseEntity<User> response = controller.deleteUser(MOCK_CPF);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(null, response.getBody());
        verify(userService, times(1)).deleteUser(MOCK_CPF);
    }
}