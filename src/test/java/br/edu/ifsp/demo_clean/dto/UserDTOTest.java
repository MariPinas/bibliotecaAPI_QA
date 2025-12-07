package br.edu.ifsp.demo_clean.dto;

import br.edu.ifsp.demo_clean.model.enums.Course;
import br.edu.ifsp.demo_clean.model.enums.UserStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDTOTest {
    private static final String TEST_NAME = "Mari Ana";
    private static final String TEST_CPF = "11122233344";
    private static final String TEST_EMAIL = "mari.ana@email.com";
    private static final Course TEST_COURSE = Course.ADS;
    private static final UserStatus TEST_STATUS = UserStatus.ACTIVE;

    @Test
    @DisplayName("Should initialize and access all fields correctly")
    void shouldInitializeAndAccessAllFields() {
        UserDTO dto = new UserDTO();
        dto.name = TEST_NAME;
        dto.cpf = TEST_CPF;
        dto.email = TEST_EMAIL;
        dto.course = TEST_COURSE;
        dto.status = TEST_STATUS;

        assertEquals(TEST_NAME, dto.name, "O campo 'name' deve ser acessível e ter o valor correto.");
        assertEquals(TEST_CPF, dto.cpf, "O campo 'cpf' deve ser acessível e ter o valor correto.");
        assertEquals(TEST_EMAIL, dto.email, "O campo 'email' deve ser acessível e ter o valor correto.");

        assertEquals(Course.ADS, dto.course, "O campo 'course' deve ser do tipo Course e ter o valor ADS.");
        assertEquals(UserStatus.ACTIVE, dto.status, "O campo 'status' deve ser do tipo UserStatus e ter o valor ACTIVE.");
    }

    @Test
    @DisplayName("Should initialize with null values")
    void shouldInitializeWithNullValues() {
        UserDTO dto = new UserDTO();
        assertNull(dto.name, "Campos String devem ser nulos por padrão.");
        assertNull(dto.course, "Campos Enum devem ser nulos por padrão.");
        dto.name = null;
        assertNull(dto.name, "O DTO deve permitir que o campo name seja nulo.");
    }
}