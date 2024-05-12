package ru.edu.java.barbershop.api.service.jpa;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.edu.java.barbershop.api.service.RecordService;
import ru.edu.java.barbershop.api.service.security.JwtService;
import ru.edu.java.barbershop.domain.entity.RecordAvailableTimeEntity;
import ru.edu.java.barbershop.domain.entity.RecordEntity;
import ru.edu.java.barbershop.domain.entity.ServiceEntity;
import ru.edu.java.barbershop.domain.entity.UserEntity;
import ru.edu.java.barbershop.domain.repository.RecordAvailableTimeRepository;
import ru.edu.java.barbershop.domain.repository.RecordRepository;
import ru.edu.java.barbershop.domain.repository.ServiceRepository;
import ru.edu.java.barbershop.domain.repository.UserRepository;
import ru.edu.java.barbershop.dto.request.RecordCreateRequest;
import ru.edu.java.barbershop.dto.response.RecordResponse;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class JpaRecordService implements RecordService {
    private final RecordRepository recordRepository;
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;
    private final RecordAvailableTimeRepository availableTimeRepository;
    private final JwtService jwtService;

    @Override
    public RecordResponse addRecord(RecordCreateRequest createRecord, String token) {
        UserEntity client = userRepository.findById(jwtService.extractUserId(token)).orElseThrow();
        UserEntity barber = userRepository.findById(createRecord.barberId()).orElseThrow();
        ServiceEntity service = serviceRepository.findById(createRecord.serviceId()).orElseThrow();
        RecordAvailableTimeEntity availableTime = availableTimeRepository.findById(createRecord.availableTimeId()).orElseThrow();
        RecordEntity record = new RecordEntity();
        record.setBarber(barber);
        record.setTimeOfRecord(availableTime);
        record.setService(service);
        record.setClient(client);
        service.addRecord(record);
        client.addRecord(record);
        return recordRepository.save(record).toResponse();
    }

    @Override
    public RecordResponse getRecord(UUID recordId) {
        return recordRepository.findById(recordId).orElseThrow().toResponse();
    }

    @Override
    public List<RecordResponse> getAllRecords(String token) {
        UserEntity user = userRepository.findById(jwtService.extractUserId(token)).orElseThrow();
        if (user.getRole().equals("client")) {
            return recordRepository.findAll().stream().filter(a -> a.getClient().getId().equals(user.getId())).map(RecordEntity::toResponse).toList();
        }
        return recordRepository.findAll().stream().filter(a -> a.getBarber().getId().equals(user.getId())).map(RecordEntity::toResponse).toList();
    }

    @Override
    public RecordResponse cancelRecord(UUID recordId) {
        RecordEntity record = recordRepository.findById(recordId).orElseThrow();
        UserEntity client = userRepository.findById(record.getClient().getId()).orElseThrow();
        client.cancelRecord(record);
        userRepository.save(client);
        recordRepository.deleteById(recordId);
        return record.toResponse();
    }
}
