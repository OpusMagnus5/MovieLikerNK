package pl.damian.bodzioch.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.damian.bodzioch.client.omdb.interfaces.OmdbApiClient;
import pl.damian.bodzioch.client.omdb.models.OmdbMovieModel;
import pl.damian.bodzioch.exception.AppException;
import pl.damian.bodzioch.mapper.OmdbMapper;
import pl.damian.bodzioch.mapper.RepositoryMapper;
import pl.damian.bodzioch.repository.MovieRepository;
import pl.damian.bodzioch.repository.entity.MovieEntity;
import pl.damian.bodzioch.service.intefraces.MovieService;
import pl.damian.bodzioch.service.model.MovieModel;

@Service
@Slf4j
@AllArgsConstructor
public class MovieServiceBean implements MovieService {

    private final MovieRepository movieRepository;
    private final OmdbApiClient omdbApiClient;

    @Override
    public void saveMovie(Long imdbId) {
        if (movieRepository.existsByImdbId(imdbId)) {
            throw new AppException("service.movieService.movieAlreadyLiked", HttpStatus.BAD_REQUEST);
        }
        OmdbMovieModel OmdbMovie = omdbApiClient.getMovie(imdbId);
        MovieModel movie = OmdbMapper.map(OmdbMovie);
        MovieEntity movieEntity = RepositoryMapper.map(movie);

        try {
            movieRepository.save(movieEntity);
        } catch (Exception e) {
            log.error("An error occurred while saving movie.", e);
            throw new AppException("service.movieService.saveError", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
