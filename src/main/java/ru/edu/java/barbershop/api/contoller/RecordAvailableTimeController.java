package ru.edu.java.barbershop.api.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.edu.java.barbershop.api.service.RecordAvailableTimeService;
import ru.edu.java.barbershop.dto.response.RecordAvailableTimeResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/time")
@CrossOrigin
public class RecordAvailableTimeController {
    private final RecordAvailableTimeService timeService;

    @GetMapping("/barber/{id}")
    public List<RecordAvailableTimeResponse> getAllTimesOfBarber(@PathVariable UUID id) {
        return timeService.getAllTimes(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void addTimesForBarbers() {
        timeService.addTimesForBarbers();
    }
}
