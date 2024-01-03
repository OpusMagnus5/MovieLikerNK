package pl.damian.bodzioch.client.omdb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.damian.bodzioch.client.omdb.models.OmdbResponseModel;
import pl.damian.bodzioch.exception.AppException;

import java.net.URI;

@Service
@Slf4j
public class OmdbApiClient {

    private final static String BASE_URL = "https://www.omdbapi.com/";
    private final static String TITLE_PARAM_NAME = "s";
    private final static String IMDB_ID_PARAM_NAME = "i";
    private final static String TYPE_PARAM_NAME = "type";
    private final static String MOVIE_TYPE_PARAM_VALUE = "movie";
    private final static String API_KEY_PARAM_NAME = "apikey";
    private final static String PAGE_PARAM_NAME = "page";

    private final RestTemplate restTemplate;
    private final String apiKey;

    public OmdbApiClient(RestTemplate restTemplate, @Value("${client.omdb.apiKey}") String apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    public ResponseEntity<OmdbResponseModel> getMovies(String searchInput) {
        URI uri = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam(TITLE_PARAM_NAME, searchInput)
                .queryParam(TYPE_PARAM_NAME, MOVIE_TYPE_PARAM_VALUE)
                .queryParam(API_KEY_PARAM_NAME, this.apiKey)
                .build()
                .toUri();
        try {
            return restTemplate.getForEntity(uri, OmdbResponseModel.class);
        } catch (RestClientException e) {
            log.error("Error occurred while making a request to the OMDB API for given input: " + searchInput, e);
            throw new AppException("client.omdb.errorGetMovies", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
