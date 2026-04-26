package com.sagar.resource;

import com.sagar.dto.LoginRequestDTO;
import com.sagar.dto.ResponseDTO;
import com.sagar.dto.TokenResponseDTO;
import com.sagar.service.AuthService;
import com.sagar.util.AppConstants;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * Public endpoint for admin login.
 * Returns a signed JWT on valid credentials; caller must include it in
 * the {@code Authorization: Bearer <token>} header for /admin/** requests.
 */
@Path("/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource extends CommonResource {

    @Inject
    AuthService authService;

    @POST
    public Uni<ResponseDTO<TokenResponseDTO>> login(@Valid LoginRequestDTO request) {
        return Uni.createFrom().item(() -> authService.login(request))
                .map(r -> buildResponse(AppConstants.AUTH_LOGIN_SUCCESS, AppConstants.STATUS_OK, r));
    }
}
