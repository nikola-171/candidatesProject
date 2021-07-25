package project.recruitment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.recruitment.model.entity.TaskEntity;
import project.recruitment.repository.TaskRepository;

@Service
@RequiredArgsConstructor
public class TaskService
{
    private final TaskRepository _taskRepository;

    // package scope since it is only used in candidate service
    // to add a new task to the database
    void addTaskToDatabase(final TaskEntity task)
    {
        _taskRepository.save(task);
    }
}
