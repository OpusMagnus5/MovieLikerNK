package pl.damian.bodzioch.client.omdb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.damian.bodzioch.client.omdb.interfaces.OmdbApiClient;
import pl.damian.bodzioch.client.omdb.models.OmdbMovieModel;
import pl.damian.bodzioch.client.omdb.models.OmdbSearchResponseModel;
import pl.damian.bodzioch.exception.AppException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OmdbApiClientBean implements OmdbApiClient {

    private final static String BASE_URL = "https://www.omdbapi.com/";
    private final static String TITLE_PARAM_NAME = "s";
    private final static String IMDB_ID_PARAM_NAME = "i";
    private final static String TYPE_PARAM_NAME = "type";
    private final static String MOVIE_TYPE_PARAM_VALUE = "movie";
    private final static String API_KEY_PARAM_NAME = "apikey";
    private final static String PAGE_PARAM_NAME = "page";

    private final RestTemplate restTemplate;
    private final String apiKey;

    public OmdbApiClientBean(RestTemplate restTemplate, @Value("${client.omdb.apiKey}") String apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    @Override
    public List<OmdbMovieModel> getMovies(String searchInput) {
        OmdbSearchResponseModel firstPage = getPageOfMovies(searchInput, 1);
        List<OmdbSearchResponseModel> movieList = getMovieList(searchInput, firstPage);
        movieList.add(0, firstPage);
        List<String> movieIds = movieList.stream()
                .map(OmdbSearchResponseModel::getSearch)
                .flatMap(List::stream)
                .map(OmdbMovieModel::getImdbID)
                .toList();
        return getMovieWithDetailsList(movieIds);
    }

    private List<OmdbSearchResponseModel> getMovieList(String searchInput, OmdbSearchResponseModel firstPage) {
        List<CompletableFuture<OmdbSearchResponseModel>> futures = new ArrayList<>();
        try (ExecutorService executorService = Executors.newCachedThreadPool()) {
            long pages = (long) Math.ceil(firstPage.getTotalResults() / 10D);
            for (int i = 2; i <= pages; i++) {
                int currentPage = i;
                futures.add(CompletableFuture.supplyAsync(() -> getPageOfMovies(searchInput, currentPage), executorService));
            }

            CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
            allFutures.get(1, TimeUnit.MINUTES);
            return futures.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList());
        } catch (ExecutionException | InterruptedException e) {
            log.error("An error occurred while waiting for futures to complete.", e);
            throw new AppException("client.omdb.errorGetMovies", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (TimeoutException e) {
            log.warn("A timeout occurred while waiting for futures to complete.");
            throw new AppException("client.omdb.timeout", HttpStatus.REQUEST_TIMEOUT);
        }
    }

    private List<OmdbMovieModel> getMovieWithDetailsList(List<String> movieIds) {
        try (ExecutorService executorService = Executors.newCachedThreadPool()){
            List<CompletableFuture<OmdbMovieModel>> futures = movieIds.parallelStream()
                    .map(id -> CompletableFuture.supplyAsync(() -> getMovieDetails(id), executorService))
                    .toList();

            CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
            allFutures.get(1, TimeUnit.MINUTES);
            return futures.stream()
                    .map(CompletableFuture::join)
                    .toList();
        } catch (ExecutionException | InterruptedException e) {
            log.error("An error occurred while waiting for futures to complete.", e);
            throw new AppException("client.omdb.errorGetMovies", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (TimeoutException e) {
            log.warn("A timeout occurred while waiting for futures to complete.");
            throw new AppException("client.omdb.timeout", HttpStatus.REQUEST_TIMEOUT);
        }
    }

    private OmdbSearchResponseModel getPageOfMovies(String searchInput, int page) {
        URI uri = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam(TITLE_PARAM_NAME, searchInput)
                .queryParam(TYPE_PARAM_NAME, MOVIE_TYPE_PARAM_VALUE)
                .queryParam(API_KEY_PARAM_NAME, this.apiKey)
                .queryParam(PAGE_PARAM_NAME, page)
                .build()
                .toUri();
        try {
            OmdbSearchResponseModel pageOfMovies = restTemplate.getForEntity(uri, OmdbSearchResponseModel.class).getBody();
            if (pageOfMovies == null || !pageOfMovies.getResponse()) {
                throw new AppException("client.omdb.movieNotFound", HttpStatus.NOT_FOUND);
            }
            return pageOfMovies;
        } catch (RestClientException e) {
            log.error("Error occurred while making a request to the OMDB API for given input: " + searchInput, e);
            throw new AppException("client.omdb.errorGetMovies", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private OmdbMovieModel getMovieDetails(String imdbId) {
        URI uri = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam(API_KEY_PARAM_NAME, this.apiKey)
                .queryParam(IMDB_ID_PARAM_NAME, imdbId)
                .build()
                .toUri();

        try {
            OmdbMovieModel movie = restTemplate.getForEntity(uri, OmdbMovieModel.class).getBody();
            if (movie == null || !movie.getResponse()) {
                throw new AppException("client.omdb.movieNotFound", HttpStatus.NOT_FOUND);
            }
            return movie;
        } catch (RestClientException e) {
            log.error("Error occurred while making a request to the OMDB API for imdbId: " + imdbId, e);
            throw new AppException("client.omdb.errorGetMovies", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
