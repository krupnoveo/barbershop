package ru.edu.java.barbershop.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.UUID;

public record RecordAvailableTimeCreateRequest(
        OffsetDateTime time,
        @JsonProperty("barber_id") UUID barberId
) {
}
