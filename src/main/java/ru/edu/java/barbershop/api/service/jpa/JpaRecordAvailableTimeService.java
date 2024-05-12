package ru.edu.java.barbershop.api.service.jpa;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.edu.java.barbershop.api.service.RecordAvailableTimeService;
import ru.edu.java.barbershop.domain.entity.RecordAvailableTimeEntity;
import ru.edu.java.barbershop.domain.entity.UserEntity;
import ru.edu.java.barbershop.domain.repository.RecordAvailableTimeRepository;
import ru.edu.java.barbershop.domain.repository.UserRepository;
import ru.edu.java.barbershop.dto.request.RecordAvailableTimeCreateRequest;
import ru.edu.java.barbershop.dto.response.RecordAvailableTimeResponse;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class JpaRecordAvailableTimeService implements RecordAvailableTimeService {
    private final UserRepository userRepository;
    private final RecordAvailableTimeRepository timeRepository;

    @Override
    public List<RecordAvailableTimeResponse> getAllTimes(UUID barberId) {
        UserEntity barber = userRepository.findById(barberId).orElseThrow();
        return barber.getAvailableTime().stream().map(RecordAvailableTimeEntity::toResponse).toList();
    }

    @Override
    public RecordAvailableTimeResponse getTime(UUID timeId) {
        return timeRepository.findById(timeId).orElseThrow().toResponse();
    }

    @Override
    public RecordAvailableTimeResponse addTime(RecordAvailableTimeCreateRequest request) {
        RecordAvailableTimeEntity time = timeRepository.findByTimeAndBarberId(request.time(), request.barberId()).orElseThrow();
        UserEntity barber = userRepository.findById(request.barberId()).orElseThrow();
        barber.addAvailableTime(time);
        return timeRepository.save(time).toResponse();
    }


    @Override
    public RecordAvailableTimeResponse deleteTime(UUID timeId, UUID barberId) {
        RecordAvailableTimeEntity time = timeRepository.findById(timeId).orElseThrow();
        UserEntity barber = userRepository.findById(barberId).orElseThrow();
        barber.removeAvailableTime(time);
        userRepository.save(barber);
        timeRepository.deleteById(timeId);
        return time.toResponse();
    }

    @Override
    public void addTimesForBarbers() {
        var year = OffsetDateTime.now().getYear();
        var month = OffsetDateTime.now().getMonthValue();
        var day = OffsetDateTime.now().getDayOfMonth();
        OffsetDateTime start = OffsetDateTime.of(year, month, day, 10, 0, 0, 0, ZoneOffset.ofHours(3));
        List<UserEntity> barbers = userRepository.findAll().stream().filter(a -> a.getRole().equals("barber")).toList();
        for (int k = 0; k < 7; k++) {
            for (UserEntity barber : barbers) {
                for (int j = 0; j < 12; j++) {
                    if (!timeRepository.existsByTimeAndBarberId(start, barber.getId())) {
                        RecordAvailableTimeEntity time = new RecordAvailableTimeEntity();
                        time.setTime(start);
                        barber.addAvailableTime(time);
                        timeRepository.save(time);
                    }
                    start = start.plusHours(1);
                }
            }
            year = OffsetDateTime.now().plusDays(k+1).getYear();
            month = OffsetDateTime.now().plusDays(k+1).getMonthValue();
            day = OffsetDateTime.now().plusDays(k+1).getDayOfMonth();
            start = OffsetDateTime.of(year, month, day, 10, 0, 0, 0, ZoneOffset.ofHours(3));
        }
    }
}
