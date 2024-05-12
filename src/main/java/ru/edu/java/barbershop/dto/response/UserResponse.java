package ru.edu.java.barbershop.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import java.util.Set;
import java.util.UUID;

@Builder
public record UserResponse(
        UUID id,
        String email,
        String role,
        @JsonProperty("phone_number") String phoneNumber,
        @JsonProperty("full_name") String fullName,
        @JsonProperty("jwt_token") String jwtToken,
        @JsonProperty("barbershop_id") UUID barbershopId,
        @JsonProperty("clients_records") Set<RecordResponse> clientRecords,
        @JsonProperty("records_of_clients") Set<RecordResponse> recordsOfClients,
        @JsonProperty("available_time") Set<RecordAvailableTimeResponse> availableTime

) {
}
