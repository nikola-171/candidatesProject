package project.recruitment.utils.entityModel;

import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import project.recruitment.model.Role;
import project.recruitment.model.UserDetailsImpl;
import project.recruitment.model.dto.candidate.CandidateDTO;
import project.recruitment.model.dto.task.TaskDTO;
import project.recruitment.rest.impl.CandidatesControllerImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class EntityModelBuilder
{
    private static List<String> getAuthorities()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = ((UserDetailsImpl) auth.getPrincipal());

        List<GrantedAuthority> authorityList = null;
        if(userDetails != null)
        {
            authorityList = userDetails.getAuthorities();
            return authorityList.stream().map((authority) -> authority.getAuthority()).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public static EntityModel<CandidateDTO> buildCandidateEntityModel(final CandidateDTO candidateDTO)
    {
        final Long id = candidateDTO.getId();

        final EntityModel<CandidateDTO> entityModel = EntityModel.of(
                candidateDTO,
                linkTo(methodOn(CandidatesControllerImpl.class).getCandidate(candidateDTO.getId())).withSelfRel());

        List<String> roles = getAuthorities();
        if(roles.contains(Role.ADMIN.name()) || roles.contains(Role.USER.name()))
        {
            if(candidateDTO.getActive())
            {
                entityModel.add(linkTo(methodOn(CandidatesControllerImpl.class).deactivateCandidate(id)).withRel("deactivate-candidate"));
            }else
            {
                entityModel.add(linkTo(methodOn(CandidatesControllerImpl.class).activateCandidate(id)).withRel("activate-candidate"));
            }
        }

        return entityModel;
    }

    public static EntityModel<TaskDTO> buildTaskDTOModel(final TaskDTO task, final Long candidateId, final boolean active)
    {
        EntityModel<TaskDTO> model = EntityModel.of(
                task,
                linkTo(methodOn(CandidatesControllerImpl.class).getTaskFromCandidate(candidateId, task.getId())).withSelfRel()
        );

        List<String> roles = getAuthorities();
        // if candidate is active
        if(active)
        {
            // task has yet to be subscribed
            if(task.getFinishDate() == null)
            {
                model.add(linkTo(methodOn(CandidatesControllerImpl.class).subscribeTaskSolution(task, candidateId, task.getId())).withRel("subscribe"));
            }
            if(roles.contains(Role.ADMIN.name()) || roles.contains(Role.USER.name()))
            {
                // task has yet to be reviewed
                if(task.getRating() == 0 && task.getFinishDate() != null)
                {
                    model.add(linkTo(methodOn(CandidatesControllerImpl.class).reviewTaskSolution(task, candidateId, task.getId())).withRel("review"));
                }
            }
        }
        return model;
    }
}
