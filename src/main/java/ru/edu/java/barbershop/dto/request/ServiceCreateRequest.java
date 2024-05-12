package ru.edu.java.barbershop.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ServiceCreateRequest(
        @JsonProperty("service_name") String serviceName,
        String description,
        Integer cost
) {
}
