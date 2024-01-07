package pl.damian.bodzioch.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import java.util.Set;

@Getter
@Setter
@Builder
@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "users_id_seq"
    )
    @SequenceGenerator(
            name = "users_id_seq",
            sequenceName = "users_id_seq"
    )
    private Long id;

    @NaturalId
    private String username;

    private String password;

    @OneToMany(mappedBy = "user")
    private Set<MovieEntity> movies;
}
