package project.recruitment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import project.recruitment.exception.CandidateActivationException;
import project.recruitment.exception.ResourceNotFoundException;
import project.recruitment.model.dto.CandidateDTO;
import project.recruitment.model.dto.TaskCreateDTO;
import project.recruitment.model.dto.TaskDTO;
import project.recruitment.model.entity.CandidateEntity;
import project.recruitment.model.entity.TaskEntity;
import project.recruitment.repository.CandidateRepository;
import project.recruitment.repository.specification.CandidateSearchSpecification;
import project.recruitment.searchOptions.CandidateSearchOptions;
import project.recruitment.utils.mapper.CandidateMapper;
import project.recruitment.utils.mapper.TaskMapper;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CandidateService
{
    private final CandidateRepository _candidateRepository;
    private final TaskService _taskService;

    // get all candidates
    @Transactional
    public List<CandidateDTO> getCandidates(final CandidateSearchOptions candidateSearchOptions,
                                            final Optional<Integer> currentPage,
                                            final Optional<Integer> itemsPerPage,
                                            final String orderDirection,
                                            final String orderColumn)
    {
        final int DEFAULT_PAGE = 0;
        final int DEFAULT_ITEMS_PER_PAGE = 5;
        final String ASC = "asc";

        Sort sort = Sort.unsorted();

        if(StringUtils.hasText(orderColumn))
        {
            sort = switch (orderColumn) {
                case "lastName" -> Sort.by("lastName");
                case "email" -> Sort.by("email");
                case "contactNumber" -> Sort.by("contactNumber");
                case "cityOfLiving" -> Sort.by("cityOfLiving");
                default -> Sort.by("firstName");
            };
        }

        if(StringUtils.hasText(orderDirection))
        {
            if (orderDirection.equalsIgnoreCase(ASC))
            {
                sort = sort.ascending();
            }
            else
            {
                sort = sort.descending();
            }
        }
        else
        {
            // default sort direction
            sort = sort.ascending();
        }

        final int currentPageTemp = currentPage.orElse(DEFAULT_PAGE);
        final int itemsPerPageTemp = itemsPerPage.orElse(DEFAULT_ITEMS_PER_PAGE);

        PageRequest request = PageRequest.of(currentPageTemp, itemsPerPageTemp, sort);

        return _candidateRepository.findAll(new CandidateSearchSpecification(candidateSearchOptions), request)
                .stream()
                .map(CandidateMapper::toDTO)
                .collect(Collectors.toList());
    }

    // delete candidate
    public void deleteCandidate(final Long id)
    {
        final CandidateEntity candidate = getCandidateFromDatabase(id);

        if(candidate.getDeleteDate() != null)
        {
            // candidate is already deleted
            throw new ResourceNotFoundException(generateCandidateNotFoundMessage(id));
        }else
        {
            candidate.setDeleteDate(ZonedDateTime.now());
            _candidateRepository.save(candidate);
        }
    }

    // get candidate
    public CandidateDTO getCandidate(final Long id)
    {
        final CandidateEntity candidate = getCandidateFromDatabase(id);

        // candidate is deleted
        if(candidate.getDeleteDate() != null)
        {
            throw new ResourceNotFoundException(generateCandidateNotFoundMessage(id));
        }
        return CandidateMapper.toDTO(candidate);
    }

    // add new candidate
    public CandidateDTO addCandidate(final CandidateDTO candidate)
    {
        return CandidateMapper.toDTO(_candidateRepository.save(CandidateMapper.toEntity(candidate)));
    }

    // activate candidate
    public CandidateDTO activateCandidate(final Long id)
    {
        CandidateEntity candidate = getCandidateFromDatabase(id);

        setCandidateActiveStatus(true, candidate);

        return CandidateMapper.toDTO(_candidateRepository.save(candidate));
    }

    // deactivate candidate
    public CandidateDTO deactivateCandidate(final Long id)
    {
        CandidateEntity candidate = getCandidateFromDatabase(id);

        setCandidateActiveStatus(false, candidate);

        return CandidateMapper.toDTO(_candidateRepository.save(candidate));
    }

    // edit candidate
    public CandidateDTO editCandidate(final CandidateDTO candidate, final Long id)
    {

        final CandidateEntity candidateDatabase = _candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(generateCandidateNotFoundMessage(id)));

        editCandidate(candidateDatabase, candidate);

        return CandidateMapper.toDTO(_candidateRepository.save(candidateDatabase));
    }

    // add a new task to database
    public void addTask(final TaskCreateDTO taskCreateDTO, final Long candidateId)
    {
        final CandidateEntity candidate = getCandidateFromDatabase(candidateId);
        final TaskEntity taskEntity = TaskMapper.toEntity(taskCreateDTO);
        taskEntity.setCandidate(candidate);

        _taskService.addTaskToDatabase(taskEntity);
    }

    // get task from a candidate by ID
    public TaskDTO getTaskFromCandidate(final long CandidateID, final long TaskID)
    {
        _candidateRepository.findById(CandidateID)
                .orElseThrow(() -> new ResourceNotFoundException(generateCandidateNotFoundMessage(CandidateID)));


        return TaskMapper.toDTO(_taskService.getTask(TaskID));
    }

    // subscribe solution to a task
    public void subscribeSolutionToTask(final Long candidateId, final Long taskId, final TaskDTO taskSubscribeDTO)
    {
        _taskService.subscribeSolutionToTask(taskSubscribeDTO, taskId);
    }

    // review a subscribed solution
    public void reviewSubscribedSolution(final TaskDTO taskDTO, final Long candidateId, final Long taskId)
    {
        _taskService.reviewSubscribedTask(taskDTO, taskId);
    }

    private String generateCandidateNotFoundMessage(final Long id)
    {
        return String.format("candidate with id '%s' is not found", id);
    }

    private String generateCandidateActivationMessage(final Long id, final boolean active)
    {
        String message = active ? "is already active" : "is already deactivated";

        return String.format("candidate with id '%s' %s", id, message);
    }

    private void setCandidateActiveStatus(final boolean status, final CandidateEntity candidate)
    {
        if(candidate.getActive().equals(status))
        {
            throw new CandidateActivationException(generateCandidateActivationMessage(candidate.getId(), status));
        }
        candidate.setActive(status);
    }

    private void editCandidate(final CandidateEntity candidateDatabase, final CandidateDTO candidate)
    {
        if(!candidateDatabase.getFirstName().equals(candidate.getFirstName()))
        {
            candidateDatabase.setFirstName(candidate.getFirstName());
        }
        if(!candidateDatabase.getLastName().equals(candidate.getLastName()))
        {
            candidateDatabase.setLastName(candidate.getLastName());
        }
        if(!candidateDatabase.getEmail().equals(candidate.getEmail()))
        {
            candidateDatabase.setEmail(candidate.getEmail());
        }
        if(!candidateDatabase.getCityOfLiving().equals(candidate.getCityOfLiving()))
        {
            candidateDatabase.setCityOfLiving(candidate.getCityOfLiving());
        }
        if(!candidateDatabase.getContactNumber().equals(candidate.getContactNumber()))
        {
            candidateDatabase.setContactNumber(candidate.getContactNumber());
        }
        if(!candidateDatabase.getDateOfBirth().equals(candidate.getDateOfBirth()))
        {
            candidateDatabase.setDateOfBirth(candidate.getDateOfBirth());
        }
    }

    private CandidateEntity getCandidateFromDatabase(final Long id)
    {
        return _candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(generateCandidateNotFoundMessage(id)));
    }



}
