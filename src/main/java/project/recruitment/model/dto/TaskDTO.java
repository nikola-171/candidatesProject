package project.recruitment.model.dto;

import lombok.Builder;
import lombok.Value;

import java.time.ZonedDateTime;

@Value
@Builder
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

    CandidateDTO candidate;
}
