package project.recruitment.model.dto.task;

import lombok.Builder;
import lombok.Value;
import org.springframework.hateoas.server.core.Relation;

import java.time.ZonedDateTime;

@Value
@Builder
@Relation(collectionRelation = "tasks", itemRelation = "task")
public class TaskDTO
{
    Long id;

    String name;

    String description;

    String language;

    String solution;

    ZonedDateTime startDate;

    ZonedDateTime finishDate;

    @Builder.Default
    Long rating = 0L;
}
