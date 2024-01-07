package pl.damian.bodzioch.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import java.util.Objects;

@Getter
@Setter
@Builder
@Entity(name = "movie")
@NoArgsConstructor
@AllArgsConstructor
public class MovieEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "movie_id_seq"
    )
    @SequenceGenerator(
            name = "movie_id_seq",
            sequenceName = "movie_id_seq"
    )
    private Long id;

    private String title;

    private Integer year;

    private String director;

    private String genre;

    private String plot;

    private String poster;

    @NaturalId
    @Column(name = "imdb_id")
    private Long imdbId;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private UserEntity user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieEntity that = (MovieEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(imdbId, that.imdbId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, imdbId);
    }
}
