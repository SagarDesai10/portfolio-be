package com.sagar.exceptions;

import com.sagar.dto.ResponseDTO;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit tests for {@link ApplicationException} and {@link GlobalExceptionHandler}.
 */
class ExceptionTest {

    // ── ApplicationException ───────────────────────────────────────────────────

    @Test
    void applicationException_storesMessageAndStatusCode() {
        ApplicationException ex = new ApplicationException("Not found", 404);
        assertThat(ex.getMessage()).isEqualTo("Not found");
        assertThat(ex.statusCode).isEqualTo(404);
        assertThat(ex.responseDTO).isNotNull();
        assertThat(ex.responseDTO).extracting("msg", "statusCode")
                .containsExactly("Not found", 404);
    }

    @Test
    void applicationException_isRuntimeException() {
        assertThat(new ApplicationException("err", 400))
                .isInstanceOf(RuntimeException.class);
    }

    // ── GlobalExceptionHandler ─────────────────────────────────────────────────

    @Test
    void globalExceptionHandler_applicationException_returnsCorrectStatus() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        ApplicationException ex = new ApplicationException("bad request", 400);

        Response response = handler.toResponse(ex);

        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.getEntity()).isInstanceOf(ResponseDTO.class);
        ResponseDTO<?> body = (ResponseDTO<?>) response.getEntity();
        assertThat(body).extracting("msg", "statusCode")
                .containsExactly("bad request", 400);
    }

    @Test
    void globalExceptionHandler_unknownException_returns500() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        RuntimeException ex = new RuntimeException("something blew up");

        Response response = handler.toResponse(ex);

        assertThat(response.getStatus()).isEqualTo(500);
        assertThat(response.getEntity()).isInstanceOf(ResponseDTO.class);
        ResponseDTO<?> body = (ResponseDTO<?>) response.getEntity();
        assertThat(body).extracting("statusCode").isEqualTo(500);
    }
}

