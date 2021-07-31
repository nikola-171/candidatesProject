package project.recruitment.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.recruitment.model.dto.CandidateDTO;
import project.recruitment.model.entity.CandidateEntity;
import project.recruitment.model.entity.TaskEntity;
import project.recruitment.repository.CandidateRepository;
import project.recruitment.repository.TaskRepository;
import project.recruitment.searchOptions.CandidateSearchOptions;
import project.recruitment.service.CandidateService;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Optional;

@Configuration
public class StartupConfiguration
{
    @Bean
    CommandLineRunner getRunner(final CandidateService candidateService, final TaskRepository repository, final CandidateRepository candidateRepository)
    {
        return args -> {
             InitializeDatabaseData(candidateService, repository, candidateRepository);
        };
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

            /*final CandidateDTO can2 = CandidateDTO
                    .builder()
                    .firstName("mika")
                    .lastName("mikic")
                    .email("mika.mikic@gmail.com")
                    .contactNumber("06552654528")
                    .cityOfLiving("belgrade")
                    .dateOfBirth(LocalDate.of(1997, 7, 21))
                    .active(true)
                    .build();

            candidateService.addCandidate(can2);*/

            /*final CandidateDTO can3 = CandidateDTO
                    .builder()
                    .firstName("sava")
                    .lastName("savic")
                    .email("sava.savic@gmail.com")
                    .contactNumber("065214528")
                    .cityOfLiving("novi sad")
                    .dateOfBirth(LocalDate.of(1990, 5, 10))
                    .active(true)
                    .build();

            candidateService.addCandidate(can3);*/

            /*final CandidateDTO can4 = CandidateDTO
                    .builder()
                    .firstName("ivko")
                    .lastName("ivkovic")
                    .email("ivko.ivkovic@gmail.com")
                    .contactNumber("06234528")
                    .cityOfLiving("nis")
                    .dateOfBirth(LocalDate.of(1992, 2, 13))
                    .active(true)
                    .build();

            candidateService.addCandidate(can4);*/

            /*final CandidateDTO can5 = CandidateDTO
                    .builder()
                    .firstName("marko")
                    .lastName("markovic")
                    .email("marko.markovic@gmail.com")
                    .contactNumber("0645896655")
                    .cityOfLiving("nis")
                    .dateOfBirth(LocalDate.of(2000, 4, 24))
                    .active(true)
                    .build();

            candidateService.addCandidate(can5);*/

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
