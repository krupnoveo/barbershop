package ru.edu.java.barbershop.api.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.edu.java.barbershop.api.service.RecordService;
import ru.edu.java.barbershop.dto.request.RecordCreateRequest;
import ru.edu.java.barbershop.dto.response.RecordResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/record")
@CrossOrigin
public class RecordController {
    private final RecordService recordService;

    @GetMapping("/{id}")
    public ResponseEntity<RecordResponse> getRecordById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(recordService.getRecord(id));
    }

    @GetMapping
    public ResponseEntity<List<RecordResponse>> getAllRecords(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(recordService.getAllRecords(token.split(" ")[1]));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CLIENT', 'ADMIN')")
    public ResponseEntity<RecordResponse> addRecord(
            @RequestHeader("Authorization") String token,
            @RequestBody RecordCreateRequest request
    ) {
        return ResponseEntity.ok().body(recordService.addRecord(request, token.split(" ")[1]));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CLIENT', 'ADMIN')")
    public ResponseEntity<RecordResponse> cancelRecord(@PathVariable UUID id) {
        return ResponseEntity.ok().body(recordService.cancelRecord(id));
    }
}
