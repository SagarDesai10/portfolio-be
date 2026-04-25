package com.sagar.service.impl;

import com.sagar.dto.LoginRequestDTO;
import com.sagar.dto.TokenResponseDTO;
import com.sagar.exceptions.ApplicationException;
import com.sagar.service.AuthService;
import com.sagar.util.AppConstants;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;

/**
 * Authenticates the admin user by comparing credentials stored in
 * application.properties, then issues a signed JWT (RSA-256) valid for
 * {@link AppConstants#JWT_EXPIRY_MINUTES} minutes.
 *
 * Design principles applied:
 *  - SRP  : only responsible for authentication + token generation
 *  - OCP  : new auth strategies can be added via a different AuthService impl
 *  - DIP  : callers depend on the AuthService interface, not this class
 */
@ApplicationScoped
public class AuthServiceImpl implements AuthService {

    @ConfigProperty(name = "app.admin.username")
    String adminUsername;

    @ConfigProperty(name = "app.admin.password")
    String adminPassword;

    @Override
    public TokenResponseDTO login(LoginRequestDTO request) {
        validateCredentials(request);
        String token = buildToken(request.getUsername());
        long expirySeconds = Duration.ofMinutes(AppConstants.JWT_EXPIRY_MINUTES).getSeconds();
        return new TokenResponseDTO(token, expirySeconds);
    }

    // ── private helpers ────────────────────────────────────────────────────────

    /**
     * Compares the supplied credentials to the configured admin account.
     * Throws 401 on mismatch.
     */
    private void validateCredentials(LoginRequestDTO request) {
        boolean usernameMatch = adminUsername.equals(request.getUsername());
        boolean passwordMatch = adminPassword.equals(request.getPassword());
        if (!usernameMatch || !passwordMatch) {
            throw new ApplicationException(
                    AppConstants.AUTH_INVALID_CREDENTIALS,
                    AppConstants.STATUS_UNAUTHORIZED
            );
        }
    }

    /**
     * Builds and signs a JWT using the RSA private key configured via
     * {@code smallrye.jwt.sign.key.location} in application.properties.
     *
     * Claims:
     *  - issuer  : portfolio-be
     *  - subject : the authenticated username
     *  - groups  : ["admin"]   → mapped to roles by SmallRye JWT
     *  - iat     : now
     *  - exp     : now + 30 min
     */
    private String buildToken(String username) {
        Instant now = Instant.now();
        Instant expiry = now.plus(Duration.ofMinutes(AppConstants.JWT_EXPIRY_MINUTES));

        return Jwt.issuer(AppConstants.JWT_ISSUER)
                .subject(username)
                .groups(Set.of(AppConstants.JWT_ROLE_ADMIN))
                .issuedAt(now)
                .expiresAt(expiry)
                .sign();
    }
}


