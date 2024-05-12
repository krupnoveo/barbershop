package ru.edu.java.barbershop.api.service.authentication;

import ru.edu.java.barbershop.dto.request.AuthenticationRequest;
import ru.edu.java.barbershop.dto.request.RegistrationRequest;
import ru.edu.java.barbershop.dto.response.UserResponse;

public interface AuthenticationService {
    UserResponse authenticate(AuthenticationRequest request);

    UserResponse signUp(RegistrationRequest request, String role);
}
