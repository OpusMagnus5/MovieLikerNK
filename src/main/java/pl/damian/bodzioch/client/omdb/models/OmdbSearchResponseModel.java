package pl.damian.bodzioch.client.omdb.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class OmdbSearchResponseModel implements Serializable {

    @JsonProperty("Search")
    private List<OmdbMovieModel> search;

    private Long totalResults;

    @JsonProperty("Response")
    private Boolean response;
}
