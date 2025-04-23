package org.assodeo.api.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginCredentials(
        @NotBlank(message = "Username is mandatory") @Email(message = "Username must be a valid email") String username,
        @NotBlank(message = "Password is mandatory") String password
) {

}
