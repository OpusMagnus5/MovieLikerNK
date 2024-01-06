package pl.damian.bodzioch.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MovieModel {

    private Long id;
    private String title;
    private String plot;
    private List<String> genres;
    private List<String> directors;
    private Integer year;
    private String poster;
    private Long imdbID;
}
