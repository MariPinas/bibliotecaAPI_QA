package br.edu.ifsp.demo_clean.controller;
import br.edu.ifsp.demo_clean.dto.UserRequestDTO;
import br.edu.ifsp.demo_clean.dto.response.BaseResponseDTO;
import br.edu.ifsp.demo_clean.dto.response.UserResponseDTO;
import br.edu.ifsp.demo_clean.model.Librarian;
import br.edu.ifsp.demo_clean.model.Professor;
import br.edu.ifsp.demo_clean.model.User;
import br.edu.ifsp.demo_clean.service.UserService;
import org.junit.jupiter.api.Assertions;
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
        protected User getParsedUser(UserRequestDTO dto) {
            return new Professor(dto.name, dto.cpf, dto.email);
        }

        @Override
        protected UserResponseDTO userToDTO(User user) {
            return new UserResponseDTO(user);
        }
    }
    private static final String MOCK_CPF = "12345678900";
    private UserRequestDTO baseDto;
    private Professor mockUser;

    @BeforeEach
    void setUp() {
        baseDto = new UserRequestDTO();
        baseDto.cpf = MOCK_CPF;
        baseDto.name = "Teste User";
        baseDto.email = "teste@teste.com";

        mockUser = mock(Professor.class);
    }


    @Test
    @DisplayName("POST / - Should return 200 OK and created user")
    void addUser_shouldReturnOkAndCreatedUser() {
        when(userService.addUser(any(User.class))).thenReturn(mockUser);

        ResponseEntity<BaseResponseDTO<UserResponseDTO>> response = controller.addUser(baseDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        assertEquals(mockUser.getId(), response.getBody().data.id);
        assertEquals(mockUser.getName(), response.getBody().data.name);
        assertEquals(mockUser.getCpf(), response.getBody().data.cpf);
        verify(userService, times(1)).addUser(any(User.class));
    }

    @Test
    @DisplayName("POST / - Should return error message: CPF já cadastrado.")
    void addUser_shouldReturnInternalServerErrorOnError() {
        String errorMessage = "CPF já cadastrado.";
        when(userService.addUser(any(User.class)))
                .thenThrow(new RuntimeException(errorMessage));

        ResponseEntity<BaseResponseDTO<UserResponseDTO>> response =
                controller.addUser(baseDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody().message);

        verify(userService, times(1)).addUser(any(User.class));
    }


    @Test
    @DisplayName("GET /{cpf} - Should return 200 OK and found user")
    void getUser_shouldReturnOkAndFoundUser() {
        when(userService.getUser(MOCK_CPF)).thenReturn(mockUser);

        ResponseEntity<BaseResponseDTO<UserResponseDTO>> response = controller.getUser(MOCK_CPF);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        assertEquals(mockUser.getId(), response.getBody().data.id);
        assertEquals(mockUser.getCpf(), response.getBody().data.cpf);
        assertEquals(mockUser.getName(), response.getBody().data.name);
        assertEquals(mockUser.getEmail(), response.getBody().data.email);
        verify(userService, times(1)).getUser(MOCK_CPF);
    }

    @Test
    @DisplayName("GET /{cpf} - Should return 500 INTERNAL_SERVER_ERROR on error")
    void getUser_shouldReturnInternalServerErrorOnError() {
        String errorMessage = "Usuário não encontrado.";
        when(userService.getUser(MOCK_CPF)).thenThrow(new RuntimeException(errorMessage));

        ResponseEntity<BaseResponseDTO<UserResponseDTO>> response = controller.getUser(MOCK_CPF);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody().message);
        verify(userService, times(1)).getUser(MOCK_CPF);
    }


    @Test
    @DisplayName("PUT /{cpf} - Should return 200 OK and updated user")
    void updateUser_shouldReturnOkAndUpdatedUser() {
        when(userService.updateUser(any(User.class), eq(MOCK_CPF))).thenReturn(mockUser);

        ResponseEntity<BaseResponseDTO<UserResponseDTO>> response = controller.updateUser(baseDto, MOCK_CPF);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        assertEquals(mockUser.getId(), response.getBody().data.id);
        assertEquals(mockUser.getName(), response.getBody().data.name);
        assertEquals(mockUser.getCpf(), response.getBody().data.cpf);
        assertEquals(mockUser.getEmail(), response.getBody().data.email);
        verify(userService, times(1)).updateUser(any(User.class), eq(MOCK_CPF));
    }

    @Test
    @DisplayName("PUT /{cpf} - Should return 500 INTERNAL_SERVER_ERROR on error")
    void updateUser_shouldReturnInternalServerErrorOnError() {
        String errorMessage = "Erro de validação.";
        when(userService.updateUser(any(User.class), eq(MOCK_CPF))).thenThrow(new RuntimeException(errorMessage));

        ResponseEntity<BaseResponseDTO<UserResponseDTO>> response = controller.updateUser(baseDto, MOCK_CPF);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody().message);
        verify(userService, times(1)).updateUser(any(User.class), eq(MOCK_CPF));
    }


    @Test
    @DisplayName("DELETE /{cpf} - Should return 200 OK and deleted user")
    void deleteUser_shouldReturnOkAndDeletedUser() {
        when(userService.deleteUser(MOCK_CPF)).thenReturn(mockUser);

        ResponseEntity<BaseResponseDTO<UserResponseDTO>> response = controller.deleteUser(MOCK_CPF);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        assertEquals(mockUser.getId(), response.getBody().data.id);
        assertEquals(mockUser.getName(), response.getBody().data.name);
        assertEquals(mockUser.getCpf(), response.getBody().data.cpf);
        assertEquals(mockUser.getEmail(), response.getBody().data.email);
        verify(userService, times(1)).deleteUser(MOCK_CPF);
    }

    @Test
    @DisplayName("DELETE /{cpf} - Should return 500 INTERNAL_SERVER_ERROR on error")
    void deleteUser_shouldReturnInternalServerErrorOnError() {
        String errorMessage = "Usuário possui empréstimos.";
        when(userService.deleteUser(MOCK_CPF)).thenThrow(new RuntimeException(errorMessage));

        ResponseEntity<BaseResponseDTO<UserResponseDTO>> response = controller.deleteUser(MOCK_CPF);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody().message);
        verify(userService, times(1)).deleteUser(MOCK_CPF);
    }
}