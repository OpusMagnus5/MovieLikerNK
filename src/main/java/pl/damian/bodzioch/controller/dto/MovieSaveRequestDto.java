package pl.damian.bodzioch.controller.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MovieSaveRequestDto implements Serializable {

    @NotNull(message = "controller.dto.MovieSaveRequestDto.null")
    @Max(value = 9999999, message = "controller.dto.MovieSaveRequestDto.incorrectImdbId")
    @Min(value = 1, message = "controller.dto.MovieSaveRequestDto.incorrectImdbId")
    Long imdbId;
}
