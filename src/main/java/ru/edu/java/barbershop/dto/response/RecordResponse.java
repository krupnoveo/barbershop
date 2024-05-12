package ru.edu.java.barbershop.dto.response;

import java.time.OffsetDateTime;
import java.util.UUID;

public record RecordResponse(
        UUID recordId,
        String clientName,
        String barberName,
        String serviceName,
        String barbershopAddress,
        OffsetDateTime recordTime,
        Integer cost
) {}
