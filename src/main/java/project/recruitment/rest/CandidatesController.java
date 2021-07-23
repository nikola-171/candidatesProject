package project.recruitment.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.recruitment.model.dto.CandidateDTO;

import java.util.List;
import java.util.Optional;

@RequestMapping("/candidates")
public interface CandidatesController
{
    @GetMapping("")
    List<CandidateDTO> getCandidates(@RequestParam("firstName") final String firstName,
                                     @RequestParam("lastName") final String lastName,
                                     @RequestParam("email") final String email,
                                     @RequestParam("cityOfLiving") final String cityOfLiving,
                                     @RequestParam("contactNumber") final String contactNumber,
                                     @RequestParam(value = "currentPage", required = false) final Optional<Integer> currentPage,
                                     @RequestParam(value = "itemsPerPage", required = false) final Optional<Integer> itemsPerPage,
                                     @RequestParam(value = "orderDirection", required = false) final String orderDirection,
                                     @RequestParam(value = "orderColumn", required = false) final String orderColumn);
    @GetMapping("/{id}")
    CandidateDTO getCandidate(@PathVariable final Long id);
}
