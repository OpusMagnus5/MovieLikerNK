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
import pl.damian.bodzioch.repository.UserRepository;
import pl.damian.bodzioch.repository.entity.MovieEntity;
import pl.damian.bodzioch.repository.entity.UserEntity;
import pl.damian.bodzioch.service.intefraces.MovieService;
import pl.damian.bodzioch.model.MovieModel;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@AllArgsConstructor
public class MovieServiceBean implements MovieService {

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final OmdbApiClient omdbApiClient;

    @Override
    public void saveMovie(Long imdbId, String username) {
        if (movieRepository.existsByImdbId(imdbId)) {
            throw new AppException("service.movieService.movieAlreadyLiked", HttpStatus.BAD_REQUEST);
        }
        OmdbMovieModel OmdbMovie = omdbApiClient.getMovie(imdbId);
        MovieModel movie = OmdbMapper.map(OmdbMovie);
        MovieEntity movieEntity = RepositoryMapper.map(movie);
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(AppException::getGeneralException);
        movieEntity.setUser(userEntity);

        try {
            movieRepository.save(movieEntity);
        } catch (Exception e) {
            log.error("An error occurred while saving movie.", e);
            throw new AppException("service.movieService.saveError", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Override
    public List<MovieModel> getAllMovies(String username) {
        Set<MovieEntity> movies;
        try {
            movies = movieRepository.findAllByUserUsername(username);
        } catch (Exception e) {
            log.error("An error occurred while fetching movies.", e);
            throw new AppException("service.movieService.getAllError", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }

        if (movies.isEmpty()) {
            throw new AppException("service.movieService.noMoviesFound", HttpStatus.NOT_FOUND);
        }

        return movies.stream()
                .map(RepositoryMapper::map)
                .sorted(Comparator.comparing(MovieModel::getTitle).thenComparing(MovieModel::getYear))
                .toList();
    }
}
