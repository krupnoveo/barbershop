package ru.edu.java.barbershop.dto.response;

import java.util.Set;
import java.util.UUID;

public record BarbershopResponse(
        UUID id,
        String address,
        Set<UserResponse> barbers
) {
}
