package project.recruitment.searchOptions;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class CandidateSearchOptions
{
    String firstName;

    String lastName;

    String email;

    String contactNumber;

    LocalDate dateOfBirth;

    String cityOfLiving;
}
