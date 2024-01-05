package pl.damian.bodzioch.controller.conveter;

import jakarta.annotation.Nonnull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.damian.bodzioch.controller.dto.MovieSearchRequestDto;

@Component
public class StringToMovieSearchRequestDtoConverter implements Converter<String, MovieSearchRequestDto> {
    @Override
    public MovieSearchRequestDto convert(@Nonnull String source) {
        return new MovieSearchRequestDto(source);
    }
}
