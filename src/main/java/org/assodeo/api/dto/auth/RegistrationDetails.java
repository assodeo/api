package org.assodeo.api.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record RegistrationDetails(
        @NotBlank(message = "Username is mandatory") @Email(message = "Username must be a valid email") String username,
        @NotBlank(message = "Password is mandatory")
        @Length(min = 8, message = "Password must be at least 8 characters long")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).+$", message = "Password must have at least one uppercase letter, one digit and one special character")
        String password,
        @NotBlank(message = "First name is mandatory") String firstName,
        @NotBlank(message = "Last name is mandatory") String lastName
) {

}
