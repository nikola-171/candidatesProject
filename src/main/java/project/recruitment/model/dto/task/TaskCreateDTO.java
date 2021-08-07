package project.recruitment.model.dto.task;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TaskCreateDTO
{
    String name;
    String description;
    String language;
}
