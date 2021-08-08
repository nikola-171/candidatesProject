package project.recruitment.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import project.recruitment.model.entity.CandidateEntity;

import java.util.Optional;

public interface CandidateRepository extends JpaRepository<CandidateEntity, Long>, JpaSpecificationExecutor<CandidateEntity>
{
    Optional<CandidateEntity> findByUsername(String username);
}
