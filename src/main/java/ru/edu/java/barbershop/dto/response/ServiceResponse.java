package ru.edu.java.barbershop.dto.response;

import java.util.UUID;

public record ServiceResponse(
        UUID id,
        String serviceName,
        String description,
        Integer cost
) {
}
