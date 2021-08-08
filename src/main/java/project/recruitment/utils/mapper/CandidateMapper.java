package project.recruitment.utils.mapper;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.recruitment.model.dto.candidate.CandidateCreateDTO;
import project.recruitment.model.dto.candidate.CandidateDTO;
import project.recruitment.model.dto.task.TaskDTO;
import project.recruitment.model.entity.CandidateEntity;
import project.recruitment.model.entity.TaskEntity;
import project.recruitment.rest.impl.CandidatesControllerImpl;
import project.recruitment.utils.entityModel.EntityModelBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class CandidateMapper
{
    public static CandidateDTO toDTO(final CandidateEntity candidateEntity)
    {
        List<TaskDTO> taskDTOs = new ArrayList<>();
        CandidateDTO candidateDTO = CandidateDTO.builder().build();

        for(TaskEntity task : candidateEntity.getTasks())
        {
            TaskDTO taskDTO = TaskDTO.builder()
                    .solution(task.getSolution())
                    .id(task.getId())
                    .rating(task.getRating())
                    .finishDate(task.getFinishDate())
                    .startDate(task.getStartDate())
                    .language(task.getLanguage())
                    .description(task.getDescription())
                    .name(task.getName())
                    .build();
            taskDTOs.add(taskDTO);
        }
        candidateDTO.setId(candidateEntity.getId());
        candidateDTO.setFirstName(candidateEntity.getFirstName());
        candidateDTO.setLastName(candidateEntity.getLastName());
        candidateDTO.setEmail(candidateEntity.getEmail());
        candidateDTO.setCityOfLiving(candidateEntity.getCityOfLiving());
        candidateDTO.setContactNumber(candidateEntity.getContactNumber());
        candidateDTO.setUsername(candidateEntity.getUsername());
        candidateDTO.setDateOfBirth(candidateEntity.getDateOfBirth());
        candidateDTO.setActive(candidateEntity.getActive());

        CollectionModel<EntityModel<TaskDTO>> model = CollectionModel.of(
                taskDTOs.stream().map(task -> EntityModelBuilder.buildTaskDTOModel(task, candidateDTO.getId(), candidateDTO.getActive())).collect(Collectors.toList()),
                linkTo(methodOn(CandidatesControllerImpl.class).getCandidate(candidateDTO.getId())).withSelfRel()
        );
        candidateDTO.setTasks(model);

        return candidateDTO;
    }

    public static CandidateEntity toEntity(final CandidateDTO candidateDTO)
    {

        return CandidateEntity.builder()
                .id(candidateDTO.getId())
                .firstName(candidateDTO.getFirstName())
                .lastName(candidateDTO.getLastName())
                .email(candidateDTO.getEmail())
                .cityOfLiving(candidateDTO.getCityOfLiving())
                .contactNumber(candidateDTO.getContactNumber())
                .dateOfBirth(candidateDTO.getDateOfBirth())
                .active(candidateDTO.getActive())
                .build();
    }

    public static CandidateEntity toEntity(final CandidateCreateDTO candidateDTO)
    {
        final PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        return CandidateEntity.builder()
                .firstName(candidateDTO.getFirstName())
                .lastName(candidateDTO.getLastName())
                .email(candidateDTO.getEmail())
                .username(candidateDTO.getUsername())
                .password(encoder.encode(candidateDTO.getPassword()))
                .cityOfLiving(candidateDTO.getCityOfLiving())
                .contactNumber(candidateDTO.getContactNumber())
                .dateOfBirth(candidateDTO.getDateOfBirth())
                .active(candidateDTO.getActive())
                .build();
    }


}
