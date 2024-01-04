package pl.damian.bodzioch.client.omdb.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class OmdbMovieModel implements Serializable {

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Plot")
    private String plot;

    @JsonProperty("Genre")
    private String genre;

    @JsonProperty("Director")
    private String director;

    @JsonProperty("Year")
    private Integer year;

    @JsonProperty("Poster")
    private String poster;

    private String imdbID;

    @JsonProperty("Response")
    private Boolean response;
}
