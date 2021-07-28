package project.recruitment.rest;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.recruitment.model.dto.CandidateDTO;
import project.recruitment.model.dto.TaskCreateDTO;

import java.util.Optional;

@RequestMapping("/candidates")
public interface CandidatesController
{
    @GetMapping("")
    CollectionModel<EntityModel<CandidateDTO>> getCandidates(@RequestParam("firstName") final String firstName,
                                                             @RequestParam("lastName") final String lastName,
                                                             @RequestParam("email") final String email,
                                                             @RequestParam("cityOfLiving") final String cityOfLiving,
                                                             @RequestParam("contactNumber") final String contactNumber,
                                                             @RequestParam("currentPage") final Optional<Integer> currentPage,
                                                             @RequestParam("itemsPerPage") final Optional<Integer> itemsPerPage,
                                                             @RequestParam("orderDirection") final String orderDirection,
                                                             @RequestParam("orderColumn") final String orderColumn);
    @GetMapping("/{id}")
    EntityModel<CandidateDTO> getCandidate(@PathVariable final Long id);

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteCandidate(@PathVariable final Long id);

    @PostMapping("")
    EntityModel<CandidateDTO> addCandidate(@RequestBody final CandidateDTO candidateDTO);

    @PostMapping("/{id}/activate")
    EntityModel<CandidateDTO> activateCandidate(@PathVariable final Long id);

    @PostMapping("/{id}/deactivate")
    EntityModel<CandidateDTO> deactivateCandidate(@PathVariable final Long id);

    @PutMapping("/{id}")
    ResponseEntity<?> editCandidate(@RequestBody final CandidateDTO candidateDTO, @PathVariable final Long id);

    @PostMapping("/{id}/tasks")
    ResponseEntity<?> addTask(@RequestBody final TaskCreateDTO taskCreateDTO, @PathVariable final Long id);

    @GetMapping("/{candidateId}/tasks/{taskId}")
    ResponseEntity<?> getTaskFromCandidate(@PathVariable final Long candidateId, @PathVariable final Long taskId);
}
