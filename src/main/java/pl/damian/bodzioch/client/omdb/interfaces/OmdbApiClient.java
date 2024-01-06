package pl.damian.bodzioch.client.omdb.interfaces;

import pl.damian.bodzioch.client.omdb.models.OmdbMovieModel;

import java.util.List;

public interface OmdbApiClient {

    List<OmdbMovieModel> getMovies(String searchInput);

    OmdbMovieModel getMovie(Long imdbId);
}
