package project.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.recruitment.model.entity.LogEntity;

public interface LogRepository extends JpaRepository<LogEntity, Long>
{
    Iterable<LogEntity> findByLogInfo(String logLevel);
}
