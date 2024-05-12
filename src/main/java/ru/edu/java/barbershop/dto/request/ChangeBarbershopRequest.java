package ru.edu.java.barbershop.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChangeBarbershopRequest(
        @JsonProperty("barbershop_address") String barbershopAddress,
        @JsonProperty("barber_email") String barberEmail
) {
}
