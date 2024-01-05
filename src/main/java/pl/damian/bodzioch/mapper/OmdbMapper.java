package pl.damian.bodzioch.mapper;

import pl.damian.bodzioch.client.omdb.models.OmdbMovieModel;
import pl.damian.bodzioch.service.model.MovieModel;

import java.util.Arrays;

public class OmdbMapper {

    public static MovieModel map(OmdbMovieModel movie) {
        return MovieModel.builder()
                .imdbID(Long.parseLong(movie.getImdbID().substring(2)))
                .poster(movie.getPoster())
                .title(movie.getTitle())
                .year(movie.getYear())
                .plot(movie.getPlot())
                .genres(Arrays.stream(movie.getGenre()
                        .split(","))
                        .map(String::trim)
                        .toList())
                .directors(Arrays.stream(movie.getDirector()
                                .split(","))
                                .map(String::trim)
                                .toList())
                .build();
    }
}
