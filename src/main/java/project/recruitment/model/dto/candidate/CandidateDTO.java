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
    Long id;

    String firstName;

    String lastName;

    String email;

    String contactNumber;

    LocalDate dateOfBirth;

    String cityOfLiving;

    Boolean active;

    CollectionModel<EntityModel<TaskDTO>> tasks;
}
