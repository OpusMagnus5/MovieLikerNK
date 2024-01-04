package pl.damian.bodzioch.controller.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Builder
@Getter
public class MovieSearchDto implements Serializable {

    private String title;
    private String plot;
    private List<String> genres;
    private String director;
    private Integer year;
    private String poster;
    private Long imdbID;
}
