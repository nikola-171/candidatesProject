package project.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.recruitment.model.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>
{
    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);
}
