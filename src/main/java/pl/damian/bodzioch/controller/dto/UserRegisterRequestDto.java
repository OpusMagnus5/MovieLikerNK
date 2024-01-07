package pl.damian.bodzioch.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class UserRegisterRequestDto implements Serializable {

    @NotEmpty(message = "controller.dto.UserRegisterRequestDto.emptyUsername")
    @Size(max = 18, message = "controller.dto.UserRegisterRequestDto.username.max18Characters")
    @Pattern(regexp = "^[a-zA-Z0-9_-]{1,18}$", message = "controller.dto.UserRegisterRequestDto.username.forbiddenCharacters")
    private String username;

    @NotEmpty(message = "controller.dto.UserRegisterRequestDto.emptyPassword")
    @Size(max = 18, message = "controller.dto.UserRegisterRequestDto.password.max18Characters")
    private String password;
}
