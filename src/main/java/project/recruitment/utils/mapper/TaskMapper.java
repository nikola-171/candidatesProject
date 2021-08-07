package project.recruitment.utils.mapper;

import project.recruitment.model.dto.task.TaskCreateDTO;
import project.recruitment.model.dto.task.TaskDTO;
import project.recruitment.model.entity.TaskEntity;

public class TaskMapper
{
    public static TaskDTO toDTO(final TaskEntity entity)
    {
        return TaskDTO.builder()
                .name(entity.getName())
                .description(entity.getDescription())
                .language(entity.getLanguage())
                .startDate(entity.getStartDate())
                .finishDate(entity.getFinishDate())
                .solution(entity.getSolution())
                .rating(entity.getRating())
                .id(entity.getId())
                .build();
    }

    public static TaskEntity toEntity(final TaskCreateDTO taskCreateDTO)
    {
        return TaskEntity.builder()
                .name(taskCreateDTO.getName())
                .description(taskCreateDTO.getDescription())
                .language(taskCreateDTO.getLanguage())
                .build();
    }
}
