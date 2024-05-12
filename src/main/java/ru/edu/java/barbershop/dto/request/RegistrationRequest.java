package ru.edu.java.barbershop.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RegistrationRequest(
        String email,
        String password,
        @JsonProperty("full_name") String fullName
) {
}
