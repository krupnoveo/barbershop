package ru.edu.java.barbershop.dto.request;

public record AuthenticationRequest(
        String email,
        String password
) {
}
