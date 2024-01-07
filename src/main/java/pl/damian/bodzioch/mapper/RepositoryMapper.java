package pl.damian.bodzioch.mapper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.damian.bodzioch.model.UserModel;
import pl.damian.bodzioch.repository.entity.MovieEntity;
import pl.damian.bodzioch.model.MovieModel;
import pl.damian.bodzioch.repository.entity.UserEntity;

import java.util.Arrays;

public class RepositoryMapper {

    private final static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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

    public static MovieModel map(MovieEntity movie) {
        return MovieModel.builder()
                .id(movie.getId())
                .imdbID(movie.getImdbId())
                .genres(Arrays.stream(movie.getGenre().split(";"))
                        .map(String::trim)
                        .toList())
                .title(movie.getTitle())
                .year(movie.getYear())
                .plot(movie.getPlot())
                .directors(Arrays.stream(movie.getDirector().split(";"))
                        .map(String::trim)
                        .toList())
                .poster(movie.getPoster())
                .build();
    }

    public static UserEntity map(UserModel user) {
        return UserEntity.builder()
                .password(passwordEncoder.encode(user.getPassword()))
                .username(user.getUsername())
                .build();
    }
}
