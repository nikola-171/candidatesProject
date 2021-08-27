package project.recruitment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import project.recruitment.exception.CandidateActivationException;
import project.recruitment.exception.ResourceNotFoundException;
import project.recruitment.exception.ReviewRangeException;
import project.recruitment.exception.UsernameTakenException;
import project.recruitment.model.dto.candidate.CandidateCreateDTO;
import project.recruitment.model.dto.candidate.CandidateDTO;
import project.recruitment.model.dto.PasswordResetRequestDTO;
import project.recruitment.model.dto.task.TaskCreateDTO;
import project.recruitment.model.dto.task.TaskDTO;
import project.recruitment.model.entity.CandidateEntity;
import project.recruitment.model.entity.TaskEntity;
import project.recruitment.model.entity.UserEntity;
import project.recruitment.repository.CandidateRepository;
import project.recruitment.repository.UserRepository;
import project.recruitment.repository.specification.CandidateSearchSpecification;
import project.recruitment.rest.impl.CandidatesControllerImpl;
import project.recruitment.searchOptions.CandidateSearchOptions;
import project.recruitment.utils.EmailSender;
import project.recruitment.utils.emailTemplate.EmailTemplate;
import project.recruitment.utils.mapper.CandidateMapper;
import project.recruitment.utils.mapper.TaskMapper;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class CandidateService
{
    private final CandidateRepository _candidateRepository;
    private final TaskService _taskService;
    private final UserRepository _userRepository;
    private final PasswordResetService _passwordResetService;

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

    // get by username
    public Optional<CandidateEntity> getCandidateByUsername(final String username)
    {
        return _candidateRepository.findByUsername(username);
    }

    // add new candidate
    public CandidateDTO addCandidate(final CandidateCreateDTO candidate)
    {
        String username = candidate.getUsername();
        // username must in unique in both user and candidate table
        // since they both can login
        Optional<UserEntity> user = _userRepository.findByUsername(username);
        Optional<CandidateEntity> candidateUser = _candidateRepository.findByUsername(username);

        if(user.isPresent() || candidateUser.isPresent())
        {
            throw new UsernameTakenException(generateUsernameAlreadyTakenMessage(username));
        }
        CandidateEntity candidateEntity = CandidateMapper.toEntity(candidate);
        CandidateEntity candidateDb = _candidateRepository.save(candidateEntity);
        return CandidateMapper.toDTO(candidateDb);
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
    public CandidateDTO editCandidate(final CandidateCreateDTO candidate, final Long id)
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
        // only if a candidate is active
        CheckIfCandidateIsActive(candidateId);
        _taskService.subscribeSolutionToTask(taskSubscribeDTO, taskId);
    }

    // review a subscribed solution
    public void reviewSubscribedSolution(final TaskDTO taskDTO, final Long candidateId, final Long taskId)
    {
        final Long rating = taskDTO.getRating();
        if(rating < 1 || rating > 100)
        {
            throw new ReviewRangeException(generateReviewRangeInvalidMessage(rating));
        }
        // only if a candidate is active
        CheckIfCandidateIsActive(candidateId);
        _taskService.reviewSubscribedTask(taskDTO, taskId);
    }

    // reset password request
    public Boolean resetPassword(final PasswordResetRequestDTO passwordResetRequestDTO) {
        String username = passwordResetRequestDTO.getUsername();
        Optional<CandidateEntity> candidate = getCandidateByUsername(username);

        if(candidate.isEmpty())
        {
            throw new ResourceNotFoundException(generateCandidateNotFoundMessage(username));
        }
        CandidateEntity entity = candidate.get();
        UUID token = UUID.randomUUID();
        String url = linkTo(methodOn(CandidatesControllerImpl.class).passwordReset(entity.getId(), token, null)).toString();
        boolean success = true;
        try
        {
            String template = EmailTemplate.getPasswordResetTemplate();
            EmailSender.SendEmail(entity.getEmail(), "Password Reset", String.format(template, url, url, url));
        }catch(Exception e)
        {
            success = false;
        }
        _passwordResetService.addRequest(token, entity.getId());
        return success;
    }

    // reset password
    public void resetPassword(final Long id, final String newPassword)
    {
        CandidateEntity candidate = getCandidateFromDatabase(id);
        candidate.setPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(newPassword));
        _candidateRepository.save(candidate);
    }

    private void CheckIfCandidateIsActive(final Long candidateId)
    {
        CandidateDTO candidate = getCandidate(candidateId);
        if(!candidate.getActive())
        {
            throw new CandidateActivationException(generateOperationFailedDueToCandidateNotActive(candidateId));
        }
    }

    private String generateCandidateNotFoundMessage(final Long id)
    {
        return String.format("candidate with id '%s' is not found", id);
    }

    private String generateCandidateNotFoundMessage(final String username)
    {
        return String.format("candidate with username '%s' is not found", username);
    }

    private String generateCandidateActivationMessage(final Long id, final boolean active)
    {
        String message = active ? "is already active" : "is already deactivated";

        return String.format("candidate with id '%s' %s", id, message);
    }

    private String generateReviewRangeInvalidMessage(final Long value)
    {
        return String.format("rating '%s' needs to be in range [1, 100]", value.toString());
    }

    private String generateOperationFailedDueToCandidateNotActive(final Long id)
    {
        return String.format("operation could not be completed because candidate with id '%s' is not active", id);

    }

    private String generateUsernameAlreadyTakenMessage(final String username)
    {
        return String.format("username '%s' is already taken", username);
    }

    private void setCandidateActiveStatus(final boolean status, final CandidateEntity candidate)
    {
        if(candidate.getActive().equals(status))
        {
            throw new CandidateActivationException(generateCandidateActivationMessage(candidate.getId(), status));
        }
        candidate.setActive(status);
    }

    private void editCandidate(final CandidateEntity candidateDatabase, final CandidateCreateDTO candidate)
    {
        String firstName = candidate.getFirstName();

        if(firstName != null)
        {
            firstName = firstName.strip().replaceAll(" +", " ");
        }
        if(StringUtils.hasText(firstName) && !candidateDatabase.getFirstName().equals(firstName))
        {
            candidateDatabase.setFirstName(firstName);
        }

        String lastName = candidate.getLastName();
        if(lastName != null)
        {
            lastName = lastName.strip().replaceAll(" +", " ");
        }
        if(StringUtils.hasText(lastName) && !candidateDatabase.getLastName().equals(lastName))
        {
            candidateDatabase.setLastName(lastName);
        }

        String email = candidate.getEmail();
        if(email != null)
        {
            email = email.strip().replaceAll(" +", "");
        }
        if(StringUtils.hasText(email) && !candidateDatabase.getEmail().equals(email))
        {
            candidateDatabase.setEmail(email);
        }

        String cityOfLiving = candidate.getCityOfLiving();
        if(cityOfLiving != null)
        {
            cityOfLiving = cityOfLiving.strip().replaceAll(" +", " ");
        }
        if(StringUtils.hasText(cityOfLiving) && !candidateDatabase.getCityOfLiving().equals(cityOfLiving))
        {
            candidateDatabase.setCityOfLiving(cityOfLiving);
        }

        String contactNumber = candidate.getCityOfLiving();
        if(contactNumber != null)
        {
            contactNumber = contactNumber.strip().replaceAll(" +", " ");
        }
        if(StringUtils.hasText(contactNumber) && !candidateDatabase.getContactNumber().equals(contactNumber))
        {
            candidateDatabase.setContactNumber(contactNumber);
        }

        if(candidate.getDateOfBirth() != null && !candidateDatabase.getDateOfBirth().equals(candidate.getDateOfBirth()))
        {
            candidateDatabase.setDateOfBirth(candidate.getDateOfBirth());
        }

        String username = candidate.getUsername();
        if(username != null)
        {
            username = username.strip().replaceAll(" +", " ");
        }
        if(StringUtils.hasText(username))
        {
            Optional<CandidateEntity> candidateExists = getCandidateByUsername(username);

            if(candidateExists.isPresent())
            {
                throw new UsernameTakenException(generateUsernameAlreadyTakenMessage(username));
            }
            else
            {
                candidateDatabase.setUsername(username);
            }
        }
    }

    private CandidateEntity getCandidateFromDatabase(final Long id)
    {
        return _candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(generateCandidateNotFoundMessage(id)));
    }

}
