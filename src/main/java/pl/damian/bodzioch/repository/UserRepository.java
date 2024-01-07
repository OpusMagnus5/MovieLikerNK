package pl.damian.bodzioch.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.Repository;
import pl.damian.bodzioch.repository.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends Repository<UserEntity, Long> {

    @Transactional
    UserEntity save(UserEntity userEntity);

    Boolean existsByUsername(String username);

    Optional<UserEntity> findByUsername(String username);
}
