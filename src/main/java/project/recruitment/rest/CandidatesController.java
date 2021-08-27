package project.recruitment.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.recruitment.model.dto.candidate.CandidateCreateDTO;
import project.recruitment.model.dto.candidate.CandidateDTO;
import project.recruitment.model.dto.PasswordResetRequestDTO;
import project.recruitment.model.dto.passwordReset.PasswordResetSubmitDTO;
import project.recruitment.model.dto.task.TaskCreateDTO;
import project.recruitment.model.dto.task.TaskDTO;

import java.util.Optional;
import java.util.UUID;

@RequestMapping("/candidates")
public interface CandidatesController
{
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("")
    @SecurityRequirement(name = "basicSecurity")
    CollectionModel<EntityModel<CandidateDTO>> getCandidates(@RequestParam("firstName") final String firstName,
                                                             @RequestParam("lastName") final String lastName,
                                                             @RequestParam("username") final String username,
                                                             @RequestParam("email") final String email,
                                                             @RequestParam("cityOfLiving") final String cityOfLiving,
                                                             @RequestParam("contactNumber") final String contactNumber,
                                                             @RequestParam("currentPage") final Optional<Integer> currentPage,
                                                             @RequestParam("itemsPerPage") final Optional<Integer> itemsPerPage,
                                                             @RequestParam("orderDirection") final String orderDirection,
                                                             @RequestParam("orderColumn") final String orderColumn);

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER') or (hasAuthority('CANDIDATE') and #id == authentication.principal.id)")
    @GetMapping("/{id}")
    @SecurityRequirement(name = "basicSecurity")
    EntityModel<CandidateDTO> getCandidate(@PathVariable final Long id);

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "basicSecurity")
    ResponseEntity<?> deleteCandidate(@PathVariable final Long id);

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @PostMapping("")
    @SecurityRequirement(name = "basicSecurity")
    EntityModel<CandidateDTO> addCandidate(@RequestBody final CandidateCreateDTO candidateDTO);

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @PostMapping("/{id}/activate")
    @SecurityRequirement(name = "basicSecurity")
    EntityModel<CandidateDTO> activateCandidate(@PathVariable final Long id);

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @PostMapping("/{id}/deactivate")
    @SecurityRequirement(name = "basicSecurity")
    EntityModel<CandidateDTO> deactivateCandidate(@PathVariable final Long id);

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER') or (hasAuthority('CANDIDATE') and #id == authentication.principal.id)")
    @PutMapping("/{id}")
    @SecurityRequirement(name = "basicSecurity")
    ResponseEntity<?> editCandidate(@RequestBody final CandidateCreateDTO candidateDTO, @PathVariable final Long id);

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @PostMapping("/{id}/tasks")
    @SecurityRequirement(name = "basicSecurity")
    ResponseEntity<?> addTask(@RequestBody final TaskCreateDTO taskCreateDTO, @PathVariable final Long id);

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER') or (hasAuthority('CANDIDATE') and #candidateId == authentication.principal.id)")
    @GetMapping("/{candidateId}/tasks/{taskId}")
    @SecurityRequirement(name = "basicSecurity")
    ResponseEntity<?> getTaskFromCandidate(@PathVariable final Long candidateId, @PathVariable final Long taskId);

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER') or (hasAuthority('CANDIDATE') and #candidateId == authentication.principal.id)")
    @PostMapping("/{candidateId}/tasks/{taskId}/subscribe")
    @SecurityRequirement(name = "basicSecurity")
    ResponseEntity<?> subscribeTaskSolution(@RequestBody final TaskDTO taskSubscribeDTO, @PathVariable final Long candidateId, @PathVariable final Long taskId);

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @PostMapping("/{candidateId}/tasks/{taskId}/review")
    @SecurityRequirement(name = "basicSecurity")
    ResponseEntity<?> reviewTaskSolution(@RequestBody final TaskDTO taskDTO, @PathVariable final Long candidateId, @PathVariable final Long taskId);

    @PostMapping("/reset-password-request")
    ResponseEntity<?> passwordResetRequest(@RequestBody final PasswordResetRequestDTO passwordResetRequestDTO);

    @PostMapping("/reset-password")
    ResponseEntity<?> passwordReset(@RequestParam("id") final Long id,
                                    @RequestParam("token") final UUID token,
                                    @RequestBody final PasswordResetSubmitDTO passwordResetSubmitDTO);
}
