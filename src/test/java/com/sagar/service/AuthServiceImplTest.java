package com.sagar.service;

import com.sagar.dto.LoginRequestDTO;
import com.sagar.dto.TokenResponseDTO;
import com.sagar.exceptions.ApplicationException;
import com.sagar.service.impl.AuthServiceImpl;
import com.sagar.util.AppConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit tests for {@link AuthServiceImpl}.
 *
 * Because AuthServiceImpl uses @ConfigProperty (not a constructor param),
 * we inject the field values via reflection in setUp() — avoiding the need
 * to spin up the Quarkus container for a pure logic test.
 */
class AuthServiceImplTest {

    private AuthServiceImpl service;

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "Admin@1234";

    @BeforeEach
    void setUp() throws Exception {
        service = new AuthServiceImpl();
        setField(service, "adminUsername", ADMIN_USERNAME);
        setField(service, "adminPassword", ADMIN_PASSWORD);
    }

    private void setField(Object target, String fieldName, String value) throws Exception {
        Field f = target.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(target, value);
    }

    private LoginRequestDTO request(String username, String password) {
        LoginRequestDTO req = new LoginRequestDTO();
        req.setUsername(username);
        req.setPassword(password);
        return req;
    }

    // ── Happy path ─────────────────────────────────────────────────────────────

    @Test
    void login_validCredentials_returnsTokenResponse() {
        TokenResponseDTO result = service.login(request(ADMIN_USERNAME, ADMIN_PASSWORD));

        assertThat(result).isNotNull();
        assertThat(result.getToken()).isNotBlank();
        assertThat(result.getExpiresInSeconds())
                .isEqualTo(AppConstants.JWT_EXPIRY_MINUTES * 60);
    }

    // ── Wrong credentials ──────────────────────────────────────────────────────

    @Test
    void login_wrongUsername_throws401() {
        assertThatThrownBy(() -> service.login(request("wrong", ADMIN_PASSWORD)))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining(AppConstants.AUTH_INVALID_CREDENTIALS)
                .extracting("statusCode").isEqualTo(AppConstants.STATUS_UNAUTHORIZED);
    }

    @Test
    void login_wrongPassword_throws401() {
        assertThatThrownBy(() -> service.login(request(ADMIN_USERNAME, "wrong")))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining(AppConstants.AUTH_INVALID_CREDENTIALS)
                .extracting("statusCode").isEqualTo(AppConstants.STATUS_UNAUTHORIZED);
    }

    @Test
    void login_bothWrong_throws401() {
        assertThatThrownBy(() -> service.login(request("x", "y")))
                .isInstanceOf(ApplicationException.class)
                .extracting("statusCode").isEqualTo(AppConstants.STATUS_UNAUTHORIZED);
    }

    @Test
    void login_emptyUsername_throws401() {
        assertThatThrownBy(() -> service.login(request("", ADMIN_PASSWORD)))
                .isInstanceOf(ApplicationException.class)
                .extracting("statusCode").isEqualTo(AppConstants.STATUS_UNAUTHORIZED);
    }
}

