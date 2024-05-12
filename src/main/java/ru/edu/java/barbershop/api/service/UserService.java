package ru.edu.java.barbershop.api.service;

import ru.edu.java.barbershop.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse getUser(String token);

    UserResponse changeName(String token, String name);

    UserResponse changePhoneNumber(String token, String phoneNumber);

    UserResponse changeEmail(String token, String email);
}
