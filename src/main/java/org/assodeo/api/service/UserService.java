package org.assodeo.api.service;

import org.assodeo.api.dto.auth.RegistrationDetails;
import org.assodeo.api.entity.User;
import org.assodeo.api.exception.UsernameAlreadyExistsException;
import org.assodeo.api.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Creates a new user based on the provided details.
     *
     * @param details the user details
     * @return the created user
     * @throws UsernameAlreadyExistsException if a user with the same username already exists
     */
    public User create(RegistrationDetails details) {
        if (userRepository.existsByUsername(details.username())) {
            throw new UsernameAlreadyExistsException(details.username());
        }

        User user = new User();
        user.setUsername(details.username());
        user.setPassword(passwordEncoder.encode(details.password()));
        user.setFirstName(details.firstName());
        user.setLastName(details.lastName());

        return userRepository.save(user);
    }
}
