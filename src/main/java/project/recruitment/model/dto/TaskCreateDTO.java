package project.recruitment.model.dto;

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
