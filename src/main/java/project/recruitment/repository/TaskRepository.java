package project.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.recruitment.model.entity.TaskEntity;

public interface TaskRepository extends JpaRepository<TaskEntity, Long>
{
}
