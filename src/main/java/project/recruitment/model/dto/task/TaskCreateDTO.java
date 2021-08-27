package project.recruitment.model.dto.task;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TaskCreateDTO
{
    @Override
    public String toString() {
        return "TaskCreateDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", language='" + language + '\'' +
                '}';
    }

    String name;
    String description;
    String language;
}
