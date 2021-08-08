package project.recruitment.model.dto.candidate;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;

@Builder
@Value
public class CandidateCreateDTO
{
    String firstName;

    String lastName;

    String username;

    String password;

    String email;

    String contactNumber;

    LocalDate dateOfBirth;

    String cityOfLiving;

    Boolean active = true;
}
