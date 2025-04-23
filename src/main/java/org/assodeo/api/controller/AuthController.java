package org.assodeo.api.controller;

import jakarta.validation.Valid;
import org.assodeo.api.dto.auth.LoginCredentials;
import org.assodeo.api.dto.auth.RegistrationDetails;
import org.assodeo.api.dto.auth.Token;
import org.assodeo.api.dto.user.UserDto;
import org.assodeo.api.entity.User;
import org.assodeo.api.security.jwt.JwtService;
import org.assodeo.api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public Token login(@RequestBody @Valid LoginCredentials credentials) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.username(),
                        credentials.password()
                )
        );

        String token = jwtService.generateToken(authentication);

        return new Token(token);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto register(@RequestBody @Valid RegistrationDetails details) {
        User user = userService.create(details);

        return UserDto.from(user);
    }
}
