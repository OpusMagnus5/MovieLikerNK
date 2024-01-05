package pl.damian.bodzioch.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.damian.bodzioch.controller.dto.MovieSearchDto;
import pl.damian.bodzioch.controller.dto.MovieSearchResponseDto;
import pl.damian.bodzioch.mapper.ControllerMapper;
import pl.damian.bodzioch.service.intefraces.OmdbService;
import pl.damian.bodzioch.service.model.MovieModel;

import java.util.List;

@RestController
@RequestMapping("/movie")
@AllArgsConstructor
public class MovieController {

    private final OmdbService omdbService;
    private final ControllerMapper mapper;

    @GetMapping("/{input}")
    public ResponseEntity<MovieSearchResponseDto> searchMovies(@PathVariable String input) {
        List<MovieModel> movieModels = this.omdbService.searchMovies(input);
        List<MovieSearchDto> movies = movieModels.stream()
                .map(this.mapper::map)
                .toList();
        MovieSearchResponseDto response = MovieSearchResponseDto.builder()
                .movies(movies)
                .build();
        return ResponseEntity.ok(response);
    }
}
