package pl.damian.bodzioch.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;

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
}
