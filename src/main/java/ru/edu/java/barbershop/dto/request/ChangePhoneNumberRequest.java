package ru.edu.java.barbershop.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChangePhoneNumberRequest(
        @JsonProperty("phone_number") String phoneNumber
) {
}
