package com.sagar.service;

import com.sagar.dto.LoginRequestDTO;
import com.sagar.dto.TokenResponseDTO;

/**
 * Contract for authentication operations.
 * Keeping auth logic separate from resource layer (SRP).
 */
public interface AuthService {

    /**
     * Validates the supplied credentials against the configured admin account
     * and returns a signed JWT on success.
     *
     * @param request login payload (username + password)
     * @return {@link TokenResponseDTO} containing the signed token and its TTL
     * @throws com.sagar.exceptions.ApplicationException with 401 when credentials are wrong
     */
    TokenResponseDTO login(LoginRequestDTO request);
}

