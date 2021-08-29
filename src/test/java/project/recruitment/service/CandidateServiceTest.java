package project.recruitment.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import project.recruitment.exception.CandidateActivationException;
import project.recruitment.exception.ResourceNotFoundException;
import project.recruitment.exception.UsernameTakenException;
import project.recruitment.model.dto.candidate.CandidateCreateDTO;
import project.recruitment.model.dto.candidate.CandidateDTO;
import project.recruitment.model.dto.task.TaskCreateDTO;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({CandidateService.class, TaskService.class, PasswordResetService.class})
class CandidateServiceTest {

    @Autowired
    private CandidateService _candidateService;

    private Long candidateId;

    @BeforeEach
    void seedData()
    {
        final CandidateCreateDTO candidate = CandidateCreateDTO.builder()
                .cityOfLiving("nis")
                .contactNumber("065852452")
                .dateOfBirth(LocalDate.of(1998, 5, 7))
                .email("candidatt@gmail.com")
                .firstName("john")
                .lastName("smith")
                .password("password")
                .username("candidate")
                .build();

        candidateId = _candidateService.addCandidate(candidate).getId();

    }
    @Test
    void shouldGetCandidates()
    {
        CandidateDTO candidate = _candidateService.getCandidate(candidateId);
        assertThat(candidate).isNotNull();

        assertThrows(ResourceNotFoundException.class, () -> _candidateService.getCandidate(2L));
    }

    @Test
    void shouldDeleteCandidate()
    {
        final CandidateCreateDTO candidate = CandidateCreateDTO.builder()
                .cityOfLiving("nis")
                .contactNumber("065852452")
                .dateOfBirth(LocalDate.of(1998, 5, 7))
                .email("candidatt@gmail.com")
                .firstName("owen")
                .lastName("smith")
                .password("password")
                .username("candidate-num2")
                .build();

       CandidateDTO candidateDb = _candidateService.addCandidate(candidate);
       assertDoesNotThrow(() -> _candidateService.deleteCandidate(candidateDb.getId()));

       assertThrows(ResourceNotFoundException.class, () -> _candidateService.deleteCandidate(candidateDb.getId()));

    }

    @Test
    void shouldAddCandidate()
    {
        final CandidateCreateDTO candidate = CandidateCreateDTO.builder()
                .cityOfLiving("nis")
                .contactNumber("065852452")
                .dateOfBirth(LocalDate.of(1998, 5, 7))
                .email("candidatt@gmail.com")
                .firstName("michael")
                .lastName("li")
                .password("password")
                .username("candidate-num3")
                .build();

        final CandidateCreateDTO candidate2 = CandidateCreateDTO.builder()
                .cityOfLiving("nis")
                .contactNumber("065852452")
                .dateOfBirth(LocalDate.of(1998, 5, 7))
                .email("candidatt@gmail.com")
                .firstName("andrew")
                .lastName("li")
                .password("password")
                .username("candidate-num3")
                .build();

        CandidateDTO candidateDb = _candidateService.addCandidate(candidate);
        assertThat(candidateDb).isNotNull();

        assertThrows(UsernameTakenException.class, () -> _candidateService.addCandidate(candidate2));

    }

    @Test
    void shouldActivateCandidate()
    {
        assertDoesNotThrow(() -> {
            _candidateService.deactivateCandidate(candidateId);
        });
        assertDoesNotThrow(() -> {
            _candidateService.activateCandidate(candidateId);
        });
        assertThrows(CandidateActivationException.class, () -> _candidateService.activateCandidate(candidateId));
    }

    @Test
    void shouldEditCandidate()
    {
        CandidateDTO candidate = _candidateService.getCandidate(candidateId);

        CandidateDTO candidateEdit = _candidateService.editCandidate(CandidateCreateDTO.builder()
                .firstName("milan")
                .build(), candidateId);

        assertThat(candidateEdit.getFirstName()).isEqualTo("milan");
        assertThat(candidateEdit.getEmail()).isEqualTo(candidate.getEmail());
    }

    @Test
    void shouldAddTask()
    {
        assertDoesNotThrow(() -> {
            CandidateDTO candidate = _candidateService.getCandidate(candidateId);

            _candidateService.addTask(TaskCreateDTO.builder()
                    .description("first task")
                    .language("java")
                    .name("create bubble sort method")
                    .build(), candidate.getId());
        });
    }
}