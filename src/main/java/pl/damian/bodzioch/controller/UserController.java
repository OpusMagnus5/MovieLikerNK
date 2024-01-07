package pl.damian.bodzioch.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.damian.bodzioch.controller.dto.BaseResponse;
import pl.damian.bodzioch.controller.dto.UserRegisterRequestDto;
import pl.damian.bodzioch.model.UserModel;
import pl.damian.bodzioch.service.intefraces.UserService;

@Tag(name = "Register controller")
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final MessageSource messageSource;

    @Operation(summary = "Registers a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "User already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BaseResponse> register(@Valid @RequestBody UserRegisterRequestDto request) {
        UserModel userModel = UserModel.builder()
                .password(request.getPassword())
                .username(request.getUsername())
                .build();
        userService.saveUser(userModel);
        String message = messageSource.getMessage("controller.userController.successfulRegister", new Object[0], LocaleContextHolder.getLocale());
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse(message));
    }
}
