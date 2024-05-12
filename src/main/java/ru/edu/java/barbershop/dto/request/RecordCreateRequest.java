package ru.edu.java.barbershop.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record RecordCreateRequest(
        @JsonProperty("service_id") UUID serviceId,
        @JsonProperty("barber_id") UUID barberId,
        @JsonProperty("available_time_id") UUID availableTimeId
) {
}
