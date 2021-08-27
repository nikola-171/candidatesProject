package project.recruitment.model.dto.candidate;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;

@Builder
@Value
public class CandidateCreateDTO
{
    @Override
    public String toString() {
        return "CandidateCreateDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", cityOfLiving='" + cityOfLiving + '\'' +
                ", active=" + active +
                '}';
    }

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
