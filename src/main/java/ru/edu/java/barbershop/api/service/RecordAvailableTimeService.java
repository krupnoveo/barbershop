package ru.edu.java.barbershop.api.service;

import ru.edu.java.barbershop.dto.request.RecordAvailableTimeCreateRequest;
import ru.edu.java.barbershop.dto.response.RecordAvailableTimeResponse;

import java.util.List;
import java.util.UUID;

public interface RecordAvailableTimeService {
    List<RecordAvailableTimeResponse> getAllTimes(UUID barberId);

    RecordAvailableTimeResponse getTime(UUID timeId);

    RecordAvailableTimeResponse addTime(RecordAvailableTimeCreateRequest request);

    RecordAvailableTimeResponse deleteTime(UUID timeId, UUID barberId);

    void addTimesForBarbers();

}
