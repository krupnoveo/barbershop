package ru.edu.java.barbershop.api.service.jpa;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.edu.java.barbershop.api.service.UserService;
import ru.edu.java.barbershop.api.service.security.JwtService;
import ru.edu.java.barbershop.domain.entity.UserEntity;
import ru.edu.java.barbershop.domain.repository.UserRepository;
import ru.edu.java.barbershop.dto.response.UserResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class JpaUserService implements UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public UserResponse getUser(String token) {
        return userRepository.findById(jwtService.extractUserId(token)).orElseThrow().toResponse().build();
    }

    @Override
    public UserResponse changeName(String token, String name) {
        UserEntity user =  userRepository.findById(jwtService.extractUserId(token)).orElseThrow();
        user.setName(name);
        userRepository.save(user);
        return user.toResponse().build();
    }

    @Override
    public UserResponse changePhoneNumber(String token, String phoneNumber) {
        UserEntity user = userRepository.findById(jwtService.extractUserId(token)).orElseThrow();
        user.setPhoneNumber(phoneNumber);
        userRepository.save(user);
        return user.toResponse().build();
    }

    @Override
    public UserResponse changeEmail(String token, String email) {
        UserEntity user = userRepository.findById(jwtService.extractUserId(token)).orElseThrow();
        user.setEmail(email);
        userRepository.save(user);
        return user.toResponse().build();
    }
}
