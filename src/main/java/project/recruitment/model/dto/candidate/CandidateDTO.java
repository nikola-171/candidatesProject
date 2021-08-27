package project.recruitment.model.dto.candidate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.core.Relation;
import project.recruitment.model.dto.task.TaskDTO;

import java.time.LocalDate;

@Builder
@Setter
@Getter
@Relation(collectionRelation = "candidates", itemRelation = "candidate")
public class CandidateDTO
{
    @Override
    public String toString() {
        return "CandidateDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", cityOfLiving='" + cityOfLiving + '\'' +
                ", active=" + active +
                '}';
    }

    Long id;

    String firstName;

    String lastName;

    String username;

    String email;

    String contactNumber;

    LocalDate dateOfBirth;

    String cityOfLiving;

    Boolean active;

    CollectionModel<EntityModel<TaskDTO>> tasks;
}
