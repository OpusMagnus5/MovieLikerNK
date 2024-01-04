package pl.damian.bodzioch.client.omdb.models;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class OmdbMovieModel implements Serializable {

    private String Title;
    private String Plot;
    private String Genre;
    private String Director;
    private Integer Year;
    private String Poster;
    private String imdbID;
    private Boolean Response;
}
