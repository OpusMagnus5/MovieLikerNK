package pl.damian.bodzioch.client.omdb.models;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class OmdbSearchResponseModel implements Serializable {

    private List<OmdbMovieModel> Search;
    private Long totalResults;
    private Boolean Response;
}
