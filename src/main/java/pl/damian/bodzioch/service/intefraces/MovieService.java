package pl.damian.bodzioch.service.intefraces;

import pl.damian.bodzioch.service.model.MovieModel;

import java.util.List;

public interface MovieService {

    void saveMovie(Long imdbId);

    List<MovieModel> getAllMovies();
}
