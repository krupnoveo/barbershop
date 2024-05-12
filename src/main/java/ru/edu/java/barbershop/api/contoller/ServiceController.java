package ru.edu.java.barbershop.api.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.edu.java.barbershop.api.service.ServiceService;
import ru.edu.java.barbershop.dto.request.ServiceChangeCostRequest;
import ru.edu.java.barbershop.dto.request.ServiceChangeDescriptionRequest;
import ru.edu.java.barbershop.dto.request.ServiceChangeNameRequest;
import ru.edu.java.barbershop.dto.request.ServiceCreateRequest;
import ru.edu.java.barbershop.dto.response.ServiceResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/service")
@CrossOrigin
public class ServiceController {
    private final ServiceService serviceService;

    @GetMapping
    public ResponseEntity<List<ServiceResponse>> getAllServices() {
        return ResponseEntity.ok().body(serviceService.getAllServices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse> getService(@PathVariable UUID id) {
        return ResponseEntity.ok().body(serviceService.getService(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceResponse> addService(@RequestBody ServiceCreateRequest request) {
        return ResponseEntity.ok().body(serviceService.addService(request.serviceName(), request.description(), request.cost()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceResponse> deleteService(@PathVariable UUID id) {
        return ResponseEntity.ok().body(serviceService.deleteService(id));
    }

    @PostMapping("/{id}/name")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceResponse> changeName(@PathVariable UUID id, ServiceChangeNameRequest request) {
        return ResponseEntity.ok().body(serviceService.changeName(id, request.name()));
    }

    @PostMapping("/{id}/description")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceResponse> changeDescription(@PathVariable UUID id, ServiceChangeDescriptionRequest request) {
        return ResponseEntity.ok().body(serviceService.changeDescription(id, request.description()));
    }

    @PostMapping("/{id}/cost")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceResponse> changeCost(@PathVariable UUID id, ServiceChangeCostRequest request) {
        return ResponseEntity.ok().body(serviceService.changeCost(id, request.cost()));
    }
}
