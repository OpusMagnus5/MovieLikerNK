package pl.damian.bodzioch.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.damian.bodzioch.controller.dto.BaseResponse;
import pl.damian.bodzioch.controller.dto.UserRegisterRequestDto;
import pl.damian.bodzioch.model.UserModel;
import pl.damian.bodzioch.service.intefraces.UserService;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final MessageSource messageSource;

    @PostMapping
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
