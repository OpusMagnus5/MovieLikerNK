package pl.damian.bodzioch.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.damian.bodzioch.controller.dto.*;
import pl.damian.bodzioch.mapper.ControllerMapper;
import pl.damian.bodzioch.service.intefraces.MovieService;
import pl.damian.bodzioch.service.intefraces.OmdbService;
import pl.damian.bodzioch.model.MovieModel;

import java.util.List;

@RestController
@RequestMapping("/movie")
@AllArgsConstructor
@Validated
public class MovieController {

    private final OmdbService omdbService;
    private final MovieService movieService;
    private final ControllerMapper mapper;
    private final MessageSource messageSource;

    @GetMapping("/{input}")
    public ResponseEntity<MovieSearchResponseDto> searchMovies(@Valid @PathVariable("input") MovieSearchRequestDto request) {
        List<MovieModel> movieModels = this.omdbService.searchMovies(request.getInput());
        List<MovieSearchDto> movies = movieModels.stream()
                .map(this.mapper::mapToMovieSearchDto)
                .toList();
        MovieSearchResponseDto response = MovieSearchResponseDto.builder()
                .movies(movies)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BaseResponse> saveMovie(@Valid @RequestBody MovieSaveRequestDto request, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getSubject();
        this.movieService.saveMovie(request.getImdbId(), username);
        String message = this.messageSource.getMessage("controller.movieController.successfulSave", new Object[0], LocaleContextHolder.getLocale());
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse(message));
    }

    @GetMapping
    public ResponseEntity<MovieGetAllResponseDto> getAll(@AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getSubject();
        List<MovieModel> allMovies = movieService.getAllMovies(username);
        List<MovieGetDto> movies = allMovies.stream()
                .map(this.mapper::mapToMovieGetDto)
                .toList();
        return ResponseEntity.ok(new MovieGetAllResponseDto(movies));
    }
}
