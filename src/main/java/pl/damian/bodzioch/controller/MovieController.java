package pl.damian.bodzioch.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Movie controller")
@RestController
@RequestMapping("/movie")
@AllArgsConstructor
@Validated
public class MovieController {

    private final OmdbService omdbService;
    private final MovieService movieService;
    private final ControllerMapper mapper;
    private final MessageSource messageSource;

    @Operation(summary = "Searches for a movie on an external OMDb service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Movie not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @SecurityRequirement(name = "bearer-key")
    @GetMapping("/{input}")
    @ResponseStatus(HttpStatus.OK)
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

    @Operation(summary = "Saves the video to your favorites list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Movie already liked"),
            @ApiResponse(responseCode = "404", description = "Movie not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @SecurityRequirement(name = "bearer-key")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BaseResponse> saveMovie(@Valid @RequestBody MovieSaveRequestDto request, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getSubject();
        this.movieService.saveMovie(request.getImdbId(), username);
        String message = this.messageSource.getMessage("controller.movieController.successfulSave", new Object[0], LocaleContextHolder.getLocale());
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse(message));
    }

    @Operation(summary = "Gets a list of all your favorite movies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Movie not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @SecurityRequirement(name = "bearer-key")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MovieGetAllResponseDto> getAll(@AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getSubject();
        List<MovieModel> allMovies = movieService.getAllMovies(username);
        List<MovieGetDto> movies = allMovies.stream()
                .map(this.mapper::mapToMovieGetDto)
                .toList();
        return ResponseEntity.ok(new MovieGetAllResponseDto(movies));
    }
}
