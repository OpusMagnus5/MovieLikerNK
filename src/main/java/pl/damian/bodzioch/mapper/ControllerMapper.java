package pl.damian.bodzioch.mapper;

import org.springframework.stereotype.Component;
import pl.damian.bodzioch.controller.dto.MovieSearchDto;
import pl.damian.bodzioch.service.model.MovieModel;

@Component
public class ControllerMapper {

    public MovieSearchDto map(MovieModel movie) {
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
}
