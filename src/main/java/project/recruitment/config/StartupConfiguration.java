package project.recruitment.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.recruitment.model.Role;
import project.recruitment.model.dto.candidate.CandidateCreateDTO;
import project.recruitment.model.dto.user.UserDTO;
import project.recruitment.model.entity.CandidateEntity;
import project.recruitment.model.entity.TaskEntity;
import project.recruitment.repository.CandidateRepository;
import project.recruitment.repository.TaskRepository;
import project.recruitment.service.CandidateService;
import project.recruitment.service.PasswordResetService;
import project.recruitment.service.UserService;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.UUID;

@Configuration
public class StartupConfiguration
{
    @Bean
    CommandLineRunner getRunner(final CandidateService candidateService, final TaskRepository repository, final CandidateRepository candidateRepository, final UserService userService, final PasswordResetService passwordResetService)
    {
        return args -> {
             //addUsers(userService, candidateService);

            //passwordResetService.addRequest(UUID.randomUUID(), 1L);
        };
    }

    private void addUsers(final UserService userService, final CandidateService candidateService)
    {
        final UserDTO user = UserDTO.builder()
                .username("user")
                .password("password")
                .firstName("pera")
                .lastName("peric")
                .email("pera.peric@gmail.com")
                .build();

        final UserDTO userAdmin = UserDTO.builder()
                .username("admin")
                .password("admin")
                .firstName("mika")
                .lastName("mikic")
                .email("mika.mikic@gmail.com")
                .build();

        final UserDTO userCandidate = UserDTO.builder()
                .username("sima")
                .password("simic")
                .firstName("mika")
                .lastName("mikic")
                .email("sima.mikic@gmail.com")
                .build();

        final CandidateCreateDTO candidate = CandidateCreateDTO.builder()
                .cityOfLiving("nis")
                .contactNumber("065852452")
                .dateOfBirth(LocalDate.of(1998, 5, 7))
                .email("candidatt@gmail.com")
                .firstName("efefe")
                .lastName("evevev")
                .password("password")
                .username("candidate")
                .build();

        final CandidateCreateDTO candidate2 = CandidateCreateDTO.builder()
                .cityOfLiving("novi sad")
                .contactNumber("065852452")
                .dateOfBirth(LocalDate.of(1998, 5, 7))
                .email("candidatt@gmail.com")
                .firstName("efefe")
                .lastName("evevev")
                .password("password")
                .username("candidateNew")
                .build();

        candidateService.addCandidate(candidate);
        candidateService.addCandidate(candidate2);

        userService.addUser(user, Arrays.asList(Role.USER));
        userService.addUser(userAdmin, Arrays.asList(Role.USER, Role.ADMIN));
        userService.addUser(userCandidate, Arrays.asList(Role.CANDIDATE));
    }

    private void InitializeDatabaseData(final CandidateService candidateService, final TaskRepository repository, final CandidateRepository candidateRepository)
    {
        // add some data if database is empty
        if(candidateRepository.findAll().size() == 0)
        {

            final CandidateEntity c = CandidateEntity
                    .builder()
                    .firstName("pera")
                    .lastName("peric")
                    .email("pera.peric@gmail.com")
                    .contactNumber("065214528")
                    .cityOfLiving("nis")
                    .dateOfBirth(LocalDate.of(1998, 5, 21))
                    .active(true)
                    .build();

            CandidateEntity can1Entity = candidateRepository.save(c);


            final TaskEntity task = TaskEntity.builder()
                    .description("task")
                    .language("c++")
                    .name("task 1")
                    .candidate(c)
                    .startDate(ZonedDateTime.now())
                    .build();

            final TaskEntity task2 = TaskEntity.builder()
                    .description("task")
                    .language("c#")
                    .name("task 2")
                    .candidate(c)
                    .startDate(ZonedDateTime.now())
                    .build();

            repository.save(task);
            repository.save(task2);
        }

    }
}
