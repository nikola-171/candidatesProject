package project.recruitment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import project.recruitment.exception.ResourceNotFoundException;
import project.recruitment.exception.SolutionAlreadySubscribedException;
import project.recruitment.exception.TaskAlreadyRatedException;
import project.recruitment.exception.TaskNotSubscribedException;
import project.recruitment.model.dto.task.TaskDTO;
import project.recruitment.model.entity.TaskEntity;
import project.recruitment.repository.TaskRepository;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class TaskService
{
    private final TaskRepository _taskRepository;

    // package scope since it is only used in candidate service
    // to add a new task to the database
    void addTaskToDatabase(final TaskEntity task)
    {
        task.setStartDate(ZonedDateTime.now());
        _taskRepository.save(task);
    }

    // get a task from a candidate by ID
    // package scope since it is only used in candidate service
    TaskEntity getTask(final long taskId)
    {
        return _taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException(generateTaskNotFoundMessage(taskId)));
    }

    // subscribe a solution to a task
    void subscribeSolutionToTask(final TaskDTO task, final Long taskId)
    {
        final TaskEntity taskFromDb = getTask(taskId);

        if(taskFromDb.getFinishDate() == null && !StringUtils.hasText(taskFromDb.getSolution()))
        {
            taskFromDb.setSolution(task.getSolution());
            taskFromDb.setFinishDate(ZonedDateTime.now());
        }
        else
        {
            throw new SolutionAlreadySubscribedException(generateSolutionAlreadyExistsMessage(taskFromDb.getId()));
        }

        _taskRepository.save(taskFromDb);
    }

    // review a subscribed task
    void reviewSubscribedTask(final TaskDTO task, final Long taskId)
    {
        final TaskEntity taskFromDb = getTask(taskId);

        if(taskFromDb.getFinishDate() != null && StringUtils.hasText(taskFromDb.getSolution()))
        {
            if(taskFromDb.getRating() == 0)
            {
                taskFromDb.setRating(task.getRating());
            }
            else
            {
                // task already rated
                throw new TaskAlreadyRatedException(generateTaskAlreadyRatedMessage(taskId));
            }
        }
        else
        {
            // task is still not subscribed
            throw new TaskNotSubscribedException(generateTaskNotSubscribedMessage(taskId));
        }
        _taskRepository.save(taskFromDb);

    }

    private String generateTaskNotFoundMessage(final Long id)
    {
        return String.format("Task with id '%s' is not found", id);
    }

    private String generateTaskAlreadyRatedMessage(final Long id)
    {
        return String.format("Task with id '%s' is already rated", id);
    }

    private String generateTaskNotSubscribedMessage(final Long id)
    {
        return String.format("Task with id '%s' is not subscribed yet, so it can not be reviewed", id);
    }

    private String generateSolutionAlreadyExistsMessage(final Long id)
    {
        return String.format("Task with id '%s' already has a solution subscribed", id);
    }
}
