package project.recruitment.rest;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.recruitment.model.dto.candidate.CandidateCreateDTO;
import project.recruitment.model.dto.candidate.CandidateDTO;
import project.recruitment.model.dto.task.TaskCreateDTO;
import project.recruitment.model.dto.task.TaskDTO;

import java.util.Optional;

@RequestMapping("/candidates")
public interface CandidatesController
{
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("")
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
    EntityModel<CandidateDTO> getCandidate(@PathVariable final Long id);

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteCandidate(@PathVariable final Long id);

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @PostMapping("")
    EntityModel<CandidateDTO> addCandidate(@RequestBody final CandidateCreateDTO candidateDTO);

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @PostMapping("/{id}/activate")
    EntityModel<CandidateDTO> activateCandidate(@PathVariable final Long id);

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @PostMapping("/{id}/deactivate")
    EntityModel<CandidateDTO> deactivateCandidate(@PathVariable final Long id);

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER') or (hasAuthority('CANDIDATE') and #id == authentication.principal.id)")
    @PutMapping("/{id}")
    ResponseEntity<?> editCandidate(@RequestBody final CandidateCreateDTO candidateDTO, @PathVariable final Long id);

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @PostMapping("/{id}/tasks")
    ResponseEntity<?> addTask(@RequestBody final TaskCreateDTO taskCreateDTO, @PathVariable final Long id);

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER') or (hasAuthority('CANDIDATE') and #candidateId == authentication.principal.id)")
    @GetMapping("/{candidateId}/tasks/{taskId}")
    ResponseEntity<?> getTaskFromCandidate(@PathVariable final Long candidateId, @PathVariable final Long taskId);

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER') or (hasAuthority('CANDIDATE') and #candidateId == authentication.principal.id)")
    @PostMapping("/{candidateId}/tasks/{taskId}/subscribe")
    ResponseEntity<?> subscribeTaskSolution(@RequestBody final TaskDTO taskSubscribeDTO, @PathVariable final Long candidateId, @PathVariable final Long taskId);

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @PostMapping("/{candidateId}/tasks/{taskId}/review")
    ResponseEntity<?> reviewTaskSolution(@RequestBody final TaskDTO taskDTO, @PathVariable final Long candidateId, @PathVariable final Long taskId);
}
