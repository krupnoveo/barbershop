package ru.edu.java.barbershop.api.service;

import ru.edu.java.barbershop.dto.request.RecordCreateRequest;
import ru.edu.java.barbershop.dto.response.RecordResponse;

import java.util.List;
import java.util.UUID;

public interface RecordService {
    RecordResponse addRecord(RecordCreateRequest createRecord, String token);

    RecordResponse getRecord(UUID recordId);

    List<RecordResponse> getAllRecords(String token);

    RecordResponse cancelRecord(UUID recordId);

}
