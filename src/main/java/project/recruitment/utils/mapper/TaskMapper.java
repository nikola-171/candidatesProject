package project.recruitment.utils.mapper;

import project.recruitment.model.dto.TaskCreateDTO;
import project.recruitment.model.dto.TaskDTO;
import project.recruitment.model.entity.TaskEntity;

public class TaskMapper
{
    public static TaskDTO toDTO(final TaskEntity entity)
    {
        return TaskDTO.builder()
                .candidate(CandidateMapper.toDTO(entity.getCandidate()))
                .name(entity.getName())
                .description(entity.getDescription())
                .language(entity.getLanguage())
                .startDate(entity.getStartDate())
                .finishDate(entity.getFinishDate())
                .rating(entity.getRating())
                .id(entity.getId())
                .build();
    }

    public static TaskEntity toEntity(final TaskDTO taskDTO)
    {
        final TaskEntity entity = TaskEntity.builder()
                .name(taskDTO.getName())
                .description(taskDTO.getDescription())
                .language(taskDTO.getLanguage())
                .startDate(taskDTO.getStartDate())
                .finishDate(taskDTO.getFinishDate())
                .rating(taskDTO.getRating())
                .id(taskDTO.getId())
                .build();

        // it can occur that a task does not have a candidate
        if(taskDTO.getCandidate() != null)
        {
            entity.setCandidate(CandidateMapper.toEntity(taskDTO.getCandidate()));
        }

        return entity;
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
