package ru.edu.java.barbershop.api.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.edu.java.barbershop.api.service.BarbershopService;
import ru.edu.java.barbershop.dto.request.BarbershopCreateRequest;
import ru.edu.java.barbershop.dto.response.BarbershopResponse;
import ru.edu.java.barbershop.dto.response.UserResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/barbershop")
@CrossOrigin
public class BarbershopController {
    private final BarbershopService barbershopService;

    @GetMapping
    public ResponseEntity<List<BarbershopResponse>> getAllBarbershops() {
        return ResponseEntity.ok().body(barbershopService.getAllBarbershops());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BarbershopResponse> addBarbershop(@RequestBody BarbershopCreateRequest request) {
        return ResponseEntity.ok().body(barbershopService.addBarbershop(request.address()));
    }

    @PostMapping("/{id}/address")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BarbershopResponse> changeAddress(@PathVariable UUID id, @RequestBody BarbershopCreateRequest request) {
        return ResponseEntity.ok().body(barbershopService.changeAddress(id, request.address()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BarbershopResponse> deleteBarbershop(@PathVariable UUID id) {
        return ResponseEntity.ok().body(barbershopService.deleteBarbershop(id));
    }

    @GetMapping("/{id}/barbers")
    public ResponseEntity<List<UserResponse>> getAllBarbersConnectedToBarbershopId(@PathVariable UUID id) {
        return ResponseEntity.ok().body(barbershopService.getAllBarbersConnectedToBarbershopId(id));
    }

    @GetMapping("/barbers")
    public ResponseEntity<List<UserResponse>> getAllBarbers() {
        return ResponseEntity.ok().body(barbershopService.getAllBarbers());
    }

    @GetMapping("/barber/{barberId}")
    public ResponseEntity<UserResponse> getBarber(@PathVariable UUID barberId) {
        return ResponseEntity.ok().body(barbershopService.getBarber(barberId));
    }

    @PostMapping("/{barbershopId}/barber/{barberId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BarbershopResponse> connectBarberToBarbershop(@PathVariable UUID barbershopId, @PathVariable UUID barberId) {
        return ResponseEntity.ok().body(barbershopService.connectBarberToBarbershop(barberId, barbershopId));
    }

    @DeleteMapping("/{barbershopId}/barber/{barberId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BarbershopResponse> disconnectBarberToBarbershop(@PathVariable UUID barbershopId, @PathVariable UUID barberId) {
        return ResponseEntity.ok().body(barbershopService.disconnectBarberFromBarbershop(barberId, barbershopId));
    }
}
