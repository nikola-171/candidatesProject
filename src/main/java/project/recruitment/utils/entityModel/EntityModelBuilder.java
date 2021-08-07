package project.recruitment.utils.entityModel;

import org.springframework.hateoas.EntityModel;
import project.recruitment.model.dto.candidate.CandidateDTO;
import project.recruitment.model.dto.task.TaskDTO;
import project.recruitment.rest.impl.CandidatesControllerImpl;

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

    public static EntityModel<TaskDTO> buildTaskDTOModel(final TaskDTO task, final Long candidateId, final boolean active)
    {
        EntityModel<TaskDTO> model = EntityModel.of(
                task,
                linkTo(methodOn(CandidatesControllerImpl.class).getTaskFromCandidate(candidateId, task.getId())).withSelfRel()
        );
        // if candidate is active
        if(active)
        {
            // task has yet to be subscribed
            if(task.getFinishDate() == null)
            {
                model.add(linkTo(methodOn(CandidatesControllerImpl.class).subscribeTaskSolution(task, candidateId, task.getId())).withRel("subscribe"));
            }
            // task has yet to be reviewed
            if(task.getRating() == 0 && task.getFinishDate() != null)
            {
                model.add(linkTo(methodOn(CandidatesControllerImpl.class).reviewTaskSolution(task, candidateId, task.getId())).withRel("review"));
            }
        }

        return model;
    }
}
