package pl.damian.bodzioch.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.damian.bodzioch.controller.dto.LoginResponseDto;
import pl.damian.bodzioch.service.intefraces.SecurityService;

@Tag(name = "Login controller")
@RestController
@AllArgsConstructor
@RequestMapping("/login")
public class AuthController {

    private final SecurityService securityService;

    @Operation(summary = "Gets the bearer token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful operation"),
            @ApiResponse(responseCode = "401", description = "Incorrect credentials"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @SecurityRequirement(name = "basicAuth")
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LoginResponseDto> login(Authentication authentication) {
        String token = securityService.generateToken(authentication);
        return ResponseEntity.ok(new LoginResponseDto(token));
    }
}
