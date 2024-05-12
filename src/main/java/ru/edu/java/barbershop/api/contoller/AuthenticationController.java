package ru.edu.java.barbershop.api.contoller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.edu.java.barbershop.dto.request.*;
import ru.edu.java.barbershop.dto.response.UserResponse;
import ru.edu.java.barbershop.api.service.authentication.AuthenticationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/client/signup")
    public ResponseEntity<UserResponse> registerClient(@RequestBody RegistrationRequest registerUserDto) {
        var user = authenticationService.signUp(registerUserDto, "client");
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + user.jwtToken())
                .body(user);
    }

    @PostMapping("/barber/signup")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> registerBarber(@RequestBody RegistrationRequest registerUserDto) {
        var user = authenticationService.signUp(registerUserDto, "barber");
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + user.jwtToken())
                .body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        var user = authenticationService.authenticate(authenticationRequest);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + user.jwtToken())
                .body(user);
    }
}
