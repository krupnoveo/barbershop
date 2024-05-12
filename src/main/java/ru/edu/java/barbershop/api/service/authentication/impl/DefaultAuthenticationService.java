package ru.edu.java.barbershop.api.service.authentication.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.edu.java.barbershop.api.service.security.JwtService;
import ru.edu.java.barbershop.domain.entity.UserEntity;
import ru.edu.java.barbershop.domain.repository.UserRepository;
import ru.edu.java.barbershop.dto.request.AuthenticationRequest;
import ru.edu.java.barbershop.dto.request.RegistrationRequest;
import ru.edu.java.barbershop.dto.response.UserResponse;
import ru.edu.java.barbershop.api.service.authentication.AuthenticationService;


@Service
@RequiredArgsConstructor
public class DefaultAuthenticationService implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public UserResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        var user = userRepository.findByEmail(request.email()).orElseThrow();
        return user.toResponse()
                .jwtToken(jwtService.generateToken(user.getEmail(), user.getId()))
                .build();
    }

    @Override
    public UserResponse signUp(RegistrationRequest request, String role) {
        if (userRepository.existsByEmail(request.email())) {
            return authenticate(new AuthenticationRequest(request.email(), request.password()));
        }
        UserEntity user = UserEntity.builder()
                .name(request.fullName())
                .email(request.email())
                .role(role)
                .password(passwordEncoder.encode(request.password()))
                .build();
        return userRepository.save(user).toResponse()
                .jwtToken(jwtService.generateToken(user.getEmail(), user.getId()))
                .build();
    }
}
