package pl.damian.bodzioch.mapper;

import pl.damian.bodzioch.repository.entity.MovieEntity;
import pl.damian.bodzioch.service.model.MovieModel;

public class RepositoryMapper {

    public static MovieEntity map(MovieModel movie) {
        return MovieEntity.builder()
                .imdbId(movie.getImdbID())
                .genre(String.join(";", movie.getGenres()))
                .title(movie.getTitle())
                .year(movie.getYear())
                .plot(movie.getPlot())
                .director(String.join(";", movie.getDirectors()))
                .poster(movie.getPoster())
                .build();
    }
}
