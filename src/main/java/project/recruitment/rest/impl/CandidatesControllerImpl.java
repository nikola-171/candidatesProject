package project.recruitment.rest.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.recruitment.model.UserDetailsImpl;
import project.recruitment.model.dto.candidate.CandidateCreateDTO;
import project.recruitment.model.dto.candidate.CandidateDTO;
import project.recruitment.model.dto.PasswordResetRequestDTO;
import project.recruitment.model.dto.passwordReset.PasswordResetSubmitDTO;
import project.recruitment.model.dto.task.TaskCreateDTO;
import project.recruitment.model.dto.task.TaskDTO;
import project.recruitment.rest.CandidatesController;
import project.recruitment.searchOptions.CandidateSearchOptions;
import project.recruitment.service.CandidateService;
import project.recruitment.service.PasswordResetService;
import project.recruitment.utils.Logger;
import project.recruitment.utils.entityModel.EntityModelBuilder;
import project.recruitment.utils.stringResources.CandidatesControllerLoggerResources;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class CandidatesControllerImpl implements CandidatesController
{
    private final CandidateService _candidateService;
    private final PasswordResetService _passwordResetService;
    private final Logger _logger;

    private UserDetailsImpl getLoggedUserDetails()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ((UserDetailsImpl) auth.getPrincipal());
    }

    @Override
    public CollectionModel<EntityModel<CandidateDTO>> getCandidates(@RequestParam(value = "firstName", required = false, defaultValue = "") final String firstName,
                                         @RequestParam(value = "lastName", required = false, defaultValue = "") final String lastName,
                                         @RequestParam(value = "username", required = false, defaultValue = "") final String username,
                                         @RequestParam(value = "email", required = false, defaultValue = "") final String email,
                                         @RequestParam(value = "cityOfLiving", required = false, defaultValue = "") final String cityOfLiving,
                                         @RequestParam(value = "contactNumber", required = false, defaultValue = "") final String contactNumber,
                                         @RequestParam(value = "currentPage", required = false, defaultValue = "0") final Optional<Integer> currentPage,
                                         @RequestParam(value = "itemsPerPage", required = false, defaultValue = "5") final Optional<Integer> itemsPerPage,
                                         @RequestParam(value = "orderDirection", required = false, defaultValue = "") final String orderDirection,
                                         @RequestParam(value = "orderColumn", required = false, defaultValue = "") final String orderColumn)
    {

        UserDetailsImpl user = getLoggedUserDetails();

        _logger.logInfo(String.format(CandidatesControllerLoggerResources.GetCandidates, user.getUsername(), user.getAuthorities().toString()));
        final List<EntityModel<CandidateDTO>> candidates = _candidateService.getCandidates(CandidateSearchOptions.builder()
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .email(email)
                .contactNumber(contactNumber)
                .cityOfLiving(cityOfLiving)
                .build(), currentPage, itemsPerPage, orderDirection, orderColumn)
                .stream()
                .map(EntityModelBuilder::buildCandidateEntityModel)
                .collect(Collectors.toList());


        return CollectionModel.of(
                candidates,
                linkTo(methodOn(CandidatesControllerImpl.class).getCandidates(firstName, lastName, username, email, cityOfLiving, contactNumber, currentPage, itemsPerPage, orderDirection, orderColumn)).withSelfRel()
        );
    }

    @Override
    public EntityModel<CandidateDTO> getCandidate(final Long id)
    {
        UserDetailsImpl user = getLoggedUserDetails();
        _logger.logInfo(String.format(CandidatesControllerLoggerResources.GetCandidate, user.getUsername(), user.getAuthorities().toString(), id.toString()));

        return EntityModelBuilder.buildCandidateEntityModel(_candidateService.getCandidate(id));
    }

    @Override
    public ResponseEntity<?> deleteCandidate(final Long id)
    {
        UserDetailsImpl user = getLoggedUserDetails();
        _logger.logInfo(String.format(CandidatesControllerLoggerResources.DeleteCandidate, user.getUsername(), user.getAuthorities().toString(), id.toString()));
        _candidateService.deleteCandidate(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public EntityModel<CandidateDTO> addCandidate(final CandidateCreateDTO candidateDTO)
    {
        UserDetailsImpl user = getLoggedUserDetails();
        _logger.logInfo(String.format(CandidatesControllerLoggerResources.AddCandidate, user.getUsername(), user.getAuthorities().toString(), candidateDTO.toString()));
         return EntityModelBuilder.buildCandidateEntityModel(_candidateService.addCandidate(candidateDTO));
    }

    @Override
    public EntityModel<CandidateDTO> activateCandidate(Long id)
    {
        UserDetailsImpl user = getLoggedUserDetails();
        _logger.logInfo(String.format(CandidatesControllerLoggerResources.ActivateCandidate, user.getUsername(), user.getAuthorities().toString(), id.toString()));
        return EntityModelBuilder.buildCandidateEntityModel(_candidateService.activateCandidate(id));
    }

    @Override
    public EntityModel<CandidateDTO> deactivateCandidate(Long id)
    {
        UserDetailsImpl user = getLoggedUserDetails();
        _logger.logInfo(String.format(CandidatesControllerLoggerResources.DeactivateCandidate, user.getUsername(), user.getAuthorities().toString(), id.toString()));
        return EntityModelBuilder.buildCandidateEntityModel(_candidateService.deactivateCandidate(id));
    }

    @Override
    public ResponseEntity<?> editCandidate(final CandidateCreateDTO candidateDTO, @PathVariable final Long id)
    {
        UserDetailsImpl user = getLoggedUserDetails();
        _logger.logInfo(String.format(CandidatesControllerLoggerResources.EditCandidate, user.getUsername(), user.getAuthorities().toString(), id.toString(), candidateDTO.toString()));
        CandidateDTO candidate = _candidateService.editCandidate(candidateDTO, id);
        return ResponseEntity.ok(candidate);
    }

    @Override
    public ResponseEntity<?> addTask(final TaskCreateDTO taskCreateDTO, final Long id)
    {
        UserDetailsImpl user = getLoggedUserDetails();
        _logger.logInfo(String.format(CandidatesControllerLoggerResources.AddTask, user.getUsername(), user.getAuthorities().toString(), id.toString(), taskCreateDTO.toString()));
        _candidateService.addTask(taskCreateDTO, id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> getTaskFromCandidate(final Long candidateId, final Long taskId)
    {
        UserDetailsImpl user = getLoggedUserDetails();
        _logger.logInfo(String.format(CandidatesControllerLoggerResources.GetTaskFromCandidate, user.getUsername(), user.getAuthorities().toString(), candidateId.toString(), taskId.toString()));
        CandidateDTO candidate = _candidateService.getCandidate(candidateId);
        return ResponseEntity.ok(EntityModelBuilder.buildTaskDTOModel(_candidateService.getTaskFromCandidate(candidateId, taskId), candidateId, candidate.getActive()));
    }

    @Override
    public ResponseEntity<?> subscribeTaskSolution(final TaskDTO taskSubscribeDTO, final Long candidateId, final Long taskId)
    {
        UserDetailsImpl user = getLoggedUserDetails();
        _logger.logInfo(String.format(CandidatesControllerLoggerResources.SubscribeTaskSolution, user.getUsername(), user.getAuthorities().toString(), candidateId.toString(), taskId.toString(), taskSubscribeDTO.getSolution()));
        _candidateService.subscribeSolutionToTask(candidateId, taskId, taskSubscribeDTO);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> reviewTaskSolution(final TaskDTO taskDTO, final Long candidateId, final Long taskId)
    {
        UserDetailsImpl user = getLoggedUserDetails();
        _logger.logInfo(String.format(CandidatesControllerLoggerResources.ReviewTaskSolution, user.getUsername(), user.getAuthorities().toString(), candidateId.toString(), taskId.toString(), taskDTO.getRating().toString()));
        _candidateService.reviewSubscribedSolution(taskDTO, candidateId, taskId);
        return ResponseEntity.noContent().build();

    }

    @Override
    public ResponseEntity<?> passwordResetRequest(PasswordResetRequestDTO passwordResetRequestDTO) {
        _logger.logInfo(String.format(CandidatesControllerLoggerResources.PasswordResetRequest, passwordResetRequestDTO.getUsername()));
        Boolean success = _candidateService.resetPassword(passwordResetRequestDTO);
        if(!success)
        {
           return ResponseEntity.ok().body(CandidatesControllerLoggerResources.operationUnavailable);
        }
        return ResponseEntity.ok(CandidatesControllerLoggerResources.emailSent);
    }

    @Override
    public ResponseEntity<?> passwordReset(Long id, UUID token, PasswordResetSubmitDTO passwordResetSubmitDTO)
    {
        _logger.logInfo(String.format(CandidatesControllerLoggerResources.PasswordResetSubmit, id.toString(), token.toString()));

        if(_passwordResetService.validateToken(token, id))
        {
            _passwordResetService.requestSubmit(token, id);
            _candidateService.resetPassword(id, passwordResetSubmitDTO.getPassword());
            _logger.logInfo(String.format(CandidatesControllerLoggerResources.PasswordResetSuccess, id.toString(), token.toString()));

            return ResponseEntity.ok(CandidatesControllerLoggerResources.passwordUpdateSuccess);
        }
        _logger.logDanger(String.format(CandidatesControllerLoggerResources.PasswordResetFail, id.toString(), token.toString()));
        return ResponseEntity.badRequest().body(CandidatesControllerLoggerResources.passwordUpdateFail);
    }

}
