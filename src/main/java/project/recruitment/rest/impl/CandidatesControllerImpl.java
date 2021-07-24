package project.recruitment.rest.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.recruitment.model.dto.CandidateDTO;
import project.recruitment.rest.CandidatesController;
import project.recruitment.searchOptions.CandidateSearchOptions;
import project.recruitment.service.CandidateService;
import project.recruitment.utils.entityModel.EntityModelBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class CandidatesControllerImpl implements CandidatesController
{
    private final CandidateService _candidateService;

    @Override
    public CollectionModel<EntityModel<CandidateDTO>> getCandidates(@RequestParam(value = "firstName", required = false, defaultValue = "") final String firstName,
                                         @RequestParam(value = "lastName", required = false, defaultValue = "") final String lastName,
                                         @RequestParam(value = "email", required = false, defaultValue = "") final String email,
                                         @RequestParam(value = "cityOfLiving", required = false, defaultValue = "") final String cityOfLiving,
                                         @RequestParam(value = "contactNumber", required = false, defaultValue = "") final String contactNumber,
                                         @RequestParam(value = "currentPage", required = false, defaultValue = "0") final Optional<Integer> currentPage,
                                         @RequestParam(value = "itemsPerPage", required = false, defaultValue = "5") final Optional<Integer> itemsPerPage,
                                         @RequestParam(value = "orderDirection", required = false, defaultValue = "") final String orderDirection,
                                         @RequestParam(value = "orderColumn", required = false, defaultValue = "") final String orderColumn)
    {

        final List<EntityModel<CandidateDTO>> candidates = _candidateService.getCandidates(CandidateSearchOptions.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .contactNumber(contactNumber)
                .cityOfLiving(cityOfLiving)
                .build(), currentPage, itemsPerPage, orderDirection, orderColumn)
                .stream()
                .map(EntityModelBuilder::buildCandidateEntityModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                candidates,
                linkTo(methodOn(CandidatesControllerImpl.class).getCandidates(firstName, lastName, email, cityOfLiving, contactNumber, currentPage, itemsPerPage, orderDirection, orderColumn)).withSelfRel()
        );
    }

    @Override
    public EntityModel<CandidateDTO> getCandidate(final Long id)
    {
        return EntityModelBuilder.buildCandidateEntityModel(_candidateService.getCandidate(id));
    }

    @Override
    public ResponseEntity<?> deleteCandidate(final Long id)
    {
        _candidateService.deleteCandidate(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public EntityModel<CandidateDTO> addCandidate(final CandidateDTO candidateDTO)
    {
         return EntityModelBuilder.buildCandidateEntityModel(_candidateService.addCandidate(candidateDTO));
    }

    @Override
    public EntityModel<CandidateDTO> activateCandidate(Long id)
    {
        return EntityModelBuilder.buildCandidateEntityModel(_candidateService.activateCandidate(id));
    }

    @Override
    public EntityModel<CandidateDTO> deactivateCandidate(Long id) {
        return EntityModelBuilder.buildCandidateEntityModel(_candidateService.deactivateCandidate(id));
    }

    @Override
    public ResponseEntity<?> editCandidate(final CandidateDTO candidateDTO, @PathVariable final Long id)
    {
        CandidateDTO candidate = _candidateService.editCandidate(candidateDTO, id);
        return ResponseEntity.ok(candidate);
    }
}
