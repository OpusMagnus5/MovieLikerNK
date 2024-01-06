package pl.damian.bodzioch.service.intefraces;

import pl.damian.bodzioch.model.MovieModel;

import java.util.List;

public interface MovieService {

    void saveMovie(Long imdbId);

    List<MovieModel> getAllMovies();
}
