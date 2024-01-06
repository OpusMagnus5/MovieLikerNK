package pl.damian.bodzioch.controller.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;
@Getter
@Builder
public class MovieGetDto implements Serializable {

    private String id;
    private String title;
    private String plot;
    private List<String> genres;
    private List<String> directors;
    private Integer year;
    private String poster;
    private Long imdbID;
}
