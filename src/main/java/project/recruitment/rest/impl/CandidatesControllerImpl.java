package project.recruitment.rest.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.recruitment.model.dto.CandidateDTO;
import project.recruitment.rest.CandidatesController;
import project.recruitment.searchOptions.CandidateSearchOptions;
import project.recruitment.service.CandidateService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CandidatesControllerImpl implements CandidatesController
{
    private final CandidateService _candidateService;

    @Override
    public List<CandidateDTO> getCandidates(@RequestParam(value = "firstName", required = false) final String firstName,
                                            @RequestParam(value = "lastName", required = false) final String lastName,
                                            @RequestParam(value = "email", required = false) final String email,
                                            @RequestParam(value = "cityOfLiving", required = false) final String cityOfLiving,
                                            @RequestParam(value = "contactNumber", required = false) final String contactNumber,
                                            @RequestParam(value = "currentPage", required = false) final Optional<Integer> currentPage,
                                            @RequestParam(value = "itemsPerPage", required = false) final Optional<Integer> itemsPerPage,
                                            @RequestParam(value = "orderDirection", required = false) final String orderDirection,
                                            @RequestParam(value = "orderColumn", required = false) final String orderColumn) {

        return _candidateService.getCandidates(CandidateSearchOptions.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .contactNumber(contactNumber)
                .cityOfLiving(cityOfLiving)
                .build(), currentPage, itemsPerPage, orderDirection, orderColumn);
    }

    @Override
    public CandidateDTO getCandidate(Long id) {
        return null;
    }
}
