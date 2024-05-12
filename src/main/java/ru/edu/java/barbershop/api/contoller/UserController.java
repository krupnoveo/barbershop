package ru.edu.java.barbershop.api.contoller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.edu.java.barbershop.api.service.UserService;
import ru.edu.java.barbershop.dto.request.ChangeEmailRequest;
import ru.edu.java.barbershop.dto.request.ChangeNameRequest;
import ru.edu.java.barbershop.dto.request.ChangePhoneNumberRequest;
import ru.edu.java.barbershop.dto.response.UserResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/me")
@CrossOrigin
public class UserController {
    private final UserService userService;

    @PostMapping("/name")
    @PreAuthorize("hasAnyRole('CLIENT', 'BARBER', 'ADMIN')")
    public ResponseEntity<UserResponse> changeName(
            @RequestBody ChangeNameRequest name,
            @RequestHeader("Authorization") String token
    ) {
        var user = userService.changeName(token.split(" ")[1], name.name());
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/phoneNumber")
    @PreAuthorize("hasAnyRole('CLIENT', 'BARBER', 'ADMIN')")
    public ResponseEntity<UserResponse> changePhoneNumber(
            @RequestBody ChangePhoneNumberRequest phoneNumber,
            @RequestHeader("Authorization") String token
    ) {
        var user = userService.changePhoneNumber(token.split(" ")[1], phoneNumber.phoneNumber());
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/email")
    @PreAuthorize("hasAnyRole('CLIENT', 'BARBER', 'ADMIN')")
    public ResponseEntity<UserResponse> changeEmail(
            @RequestBody ChangeEmailRequest email,
            @RequestHeader("Authorization") String token
    ) {
        var user = userService.changeEmail(token.split(" ")[1], email.email());
        return ResponseEntity.ok().body(user);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CLIENT', 'BARBER', 'ADMIN')")
    public ResponseEntity<UserResponse> getUser(@RequestHeader("Authorization") String token) {
        var user = userService.getUser(token.split(" ")[1]);
        return ResponseEntity.ok().body(user);
    }


}
