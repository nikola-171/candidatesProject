package project.recruitment.utils.entityModel;

import org.springframework.hateoas.EntityModel;
import project.recruitment.model.dto.CandidateDTO;
import project.recruitment.model.dto.TaskDTO;
import project.recruitment.model.entity.TaskEntity;
import project.recruitment.rest.impl.CandidatesControllerImpl;
import project.recruitment.utils.mapper.TaskMapper;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class EntityModelBuilder
{
    public static EntityModel<CandidateDTO> buildCandidateEntityModel(final CandidateDTO candidateDTO)
    {
        final Long id = candidateDTO.getId();

        final EntityModel<CandidateDTO> entityModel = EntityModel.of(
                candidateDTO,
                linkTo(methodOn(CandidatesControllerImpl.class).getCandidate(candidateDTO.getId())).withSelfRel());

        if(candidateDTO.getActive())
        {
            entityModel.add(linkTo(methodOn(CandidatesControllerImpl.class).deactivateCandidate(id)).withRel("deactivate-candidate"));
        }else
        {
            entityModel.add(linkTo(methodOn(CandidatesControllerImpl.class).activateCandidate(id)).withRel("activate-candidate"));
        }

        return entityModel;
    }

    public static EntityModel<TaskDTO> buildTaskEntityModel(final TaskEntity task)
    {
        final TaskDTO taskDTO = TaskMapper.toDTO(task);
        return EntityModel.of(
                taskDTO,
                linkTo(methodOn(CandidatesControllerImpl.class).getTaskFromCandidate(taskDTO.getCandidate().getId(), taskDTO.getId())).withSelfRel()
        );
    }
}
