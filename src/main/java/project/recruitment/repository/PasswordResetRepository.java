package project.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.recruitment.model.entity.PasswordResetEntity;

import java.util.Optional;

public interface PasswordResetRepository extends JpaRepository<PasswordResetEntity, Long>
{
    Optional<PasswordResetEntity> findByUserTokenAndUserId(String userToken, Long userId);
}
