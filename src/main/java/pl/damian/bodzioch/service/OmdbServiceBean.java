package pl.damian.bodzioch.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.damian.bodzioch.client.omdb.interfaces.OmdbApiClient;
import pl.damian.bodzioch.client.omdb.models.OmdbMovieModel;
import pl.damian.bodzioch.exception.AppException;
import pl.damian.bodzioch.mapper.OmdbMapper;
import pl.damian.bodzioch.model.MovieModel;
import pl.damian.bodzioch.service.intefraces.OmdbService;

import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class OmdbServiceBean implements OmdbService {

    private final OmdbApiClient omdbApiClient;


    public List<MovieModel> searchMovies(String input) {
        List<OmdbMovieModel> movies = this.omdbApiClient.getMovies(input);
        if (movies.isEmpty()) {
            throw new AppException("service.omdbService.movieNotFound", HttpStatus.NOT_FOUND);
        }
        return movies.stream()
                .map(OmdbMapper::map)
                .sorted(Comparator.comparing(MovieModel::getTitle).thenComparing(MovieModel::getYear))
                .toList();
    }
}
