package org.assodeo.api.service;

import org.assodeo.api.dto.auth.RegistrationDetails;
import org.assodeo.api.entity.User;
import org.assodeo.api.exception.UsernameAlreadyExistsException;
import org.assodeo.api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void createUserSuccessfullyWhenUsernameDoesNotExist() {
        RegistrationDetails details = new RegistrationDetails("newUser", "password123", "John", "Doe");

        User user = new User();
        user.setUsername(details.username());
        user.setPassword("encodedPassword");
        user.setFirstName(details.firstName());
        user.setLastName(details.lastName());

        when(userRepository.existsByUsername(details.username())).thenReturn(false);
        when(passwordEncoder.encode(details.password())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.create(details);

        assertEquals(details.username(), result.getUsername());
        assertEquals("encodedPassword", result.getPassword());
        assertEquals(details.firstName(), result.getFirstName());
        assertEquals(details.lastName(), result.getLastName());
    }

    @Test
    void throwExceptionWhenUsernameAlreadyExists() {
        RegistrationDetails details = new RegistrationDetails("existingUser", "password123", "Jane", "Doe");

        when(userRepository.existsByUsername(details.username())).thenReturn(true);

        assertThrows(UsernameAlreadyExistsException.class, () -> userService.create(details));
    }
}
