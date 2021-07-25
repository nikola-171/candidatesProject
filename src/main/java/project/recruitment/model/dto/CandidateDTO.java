package project.recruitment.model.dto;

import lombok.Builder;
import lombok.Value;
import project.recruitment.model.entity.TaskEntity;

import java.time.LocalDate;
import java.util.List;

@Value
@Builder
public class CandidateDTO
{
    Long id;

    String firstName;

    String lastName;

    String email;

    String contactNumber;

    LocalDate dateOfBirth;

    String cityOfLiving;

    Boolean active;

    List<TaskEntity> tasks;
}
