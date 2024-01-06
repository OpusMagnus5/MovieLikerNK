package pl.damian.bodzioch.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.damian.bodzioch.controller.dto.MovieGetDto;
import pl.damian.bodzioch.controller.dto.MovieSearchDto;
import pl.damian.bodzioch.service.intefraces.SecurityService;
import pl.damian.bodzioch.model.MovieModel;

@Component
@AllArgsConstructor
public class ControllerMapper {

    private final SecurityService securityService;

    public MovieSearchDto mapToMovieSearchDto(MovieModel movie) {
        return MovieSearchDto.builder()
                .plot(movie.getPlot())
                .title(movie.getTitle())
                .year(movie.getYear())
                .genres(movie.getGenres())
                .imdbID(movie.getImdbID())
                .poster(movie.getPoster())
                .directors(movie.getDirectors())
                .build();
    }

    public MovieGetDto mapToMovieGetDto(MovieModel movie) {
        return MovieGetDto.builder()
                .id(securityService.encryptMessage(movie.getId().toString()))
                .plot(movie.getPlot())
                .title(movie.getTitle())
                .year(movie.getYear())
                .genres(movie.getGenres())
                .imdbID(movie.getImdbID())
                .poster(movie.getPoster())
                .directors(movie.getDirectors())
                .build();
    }
}
