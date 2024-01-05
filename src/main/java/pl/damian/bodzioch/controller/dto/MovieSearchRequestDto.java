package pl.damian.bodzioch.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MovieSearchRequestDto implements Serializable {

    @Pattern(regexp = "^[a-zA-Z0-9 -]*$", message = "controller.dto.MovieSearchRequestDto.forbiddenCharacters")
    @Size(min = 1, max = 100, message = "controller.dto.MovieSearchRequestDto.size")
    @NotEmpty(message = "controller.dto.MovieSearchRequestDto.empty")
    private String input;
}
