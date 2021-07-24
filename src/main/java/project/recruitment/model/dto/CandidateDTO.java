package project.recruitment.model.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

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

}
