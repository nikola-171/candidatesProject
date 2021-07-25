package project.recruitment.rest.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.recruitment.model.dto.TaskDTO;
import project.recruitment.rest.TasksController;
import project.recruitment.service.TaskService;

@RestController
@RequiredArgsConstructor
public class TasksControllerImpl implements TasksController
{
    private final TaskService _taskService;


}
