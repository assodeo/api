package org.assodeo.api.security.details;

import org.assodeo.api.entity.User;
import org.assodeo.api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void loadUserByUsernameReturnsUserDetailsWhenUserExists() {
        String username = "existingUser";
        User user = new User();
        user.setId(1L);
        user.setUsername(username);
        user.setPassword("password123");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        assertEquals("1", userDetails.getUsername());
        assertEquals("password123", userDetails.getPassword());
        assertEquals(Collections.singletonList("ROLE_USER"), userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
    }

    @Test
    void loadUserByUsernameThrowsExceptionWhenUserDoesNotExist() {
        String username = "nonExistentUser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(username));
    }
}
