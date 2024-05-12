package ru.edu.java.barbershop.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.UUID;

public record RecordAvailableTimeResponse(
        UUID id,
        OffsetDateTime time,
        @JsonProperty("barber_id") UUID barberId
) {
}
