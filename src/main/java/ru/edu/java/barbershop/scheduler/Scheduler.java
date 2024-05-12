package ru.edu.java.barbershop.scheduler;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.edu.java.barbershop.domain.entity.RecordAvailableTimeEntity;
import ru.edu.java.barbershop.domain.entity.UserEntity;
import ru.edu.java.barbershop.domain.repository.RecordAvailableTimeRepository;
import ru.edu.java.barbershop.domain.repository.UserRepository;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Component
@EnableScheduling
@RequiredArgsConstructor
@Transactional
public class Scheduler {
    private final RecordAvailableTimeRepository timeRepository;
    private final UserRepository userRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void updateTimeForBarbers() {
        var year = OffsetDateTime.now().getYear();
        var month = OffsetDateTime.now().getMonthValue();
        var day = OffsetDateTime.now().plusDays(8).getDayOfMonth();
        OffsetDateTime start = OffsetDateTime.of(year, month, day, 10, 0, 0, 0, ZoneOffset.ofHours(3));
        List<UserEntity> barbers = userRepository.findAll().stream().filter(a -> a.getRole().equals("barber")).toList();
        for (UserEntity barber : barbers) {
            for (int j = 0; j < 12; j++) {
                if (!timeRepository.existsByTimeAndBarberId(start, barber.getId())) {
                    RecordAvailableTimeEntity time = new RecordAvailableTimeEntity();
                    time.setTime(start);
                    barber.addAvailableTime(time);
                    timeRepository.save(time);
                }
                start = start.plusHours(1);
                System.out.println("updating");
            }
        }
    }
}
