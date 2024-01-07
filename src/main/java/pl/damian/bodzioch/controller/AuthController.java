package pl.damian.bodzioch.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.damian.bodzioch.controller.dto.LoginResponseDto;
import pl.damian.bodzioch.service.intefraces.SecurityService;

@RestController
@AllArgsConstructor
@RequestMapping("/login")
public class AuthController {

    private final SecurityService securityService;

    @PostMapping
    public ResponseEntity<LoginResponseDto> login(Authentication authentication) {
        String token = securityService.generateToken(authentication);
        return ResponseEntity.ok(new LoginResponseDto(token));
    }
}
