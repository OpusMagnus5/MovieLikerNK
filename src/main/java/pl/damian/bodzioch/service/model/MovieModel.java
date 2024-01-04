package pl.damian.bodzioch.service.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MovieModel {

    private String title;
    private String plot;
    private List<String> genres;
    private String director;
    private Integer year;
    private String poster;
    private Long imdbID;
}
