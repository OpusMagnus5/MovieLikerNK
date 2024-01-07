package pl.damian.bodzioch.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.Repository;
import pl.damian.bodzioch.repository.entity.MovieEntity;

import java.util.Set;

public interface MovieRepository extends Repository<MovieEntity, Long> {

    @Transactional
    MovieEntity save(MovieEntity movieEntity);

    Boolean existsByImdbId(Long imdbId);

    Set<MovieEntity> findAllByUserUsername(String username);
}
