package project.recruitment.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.recruitment.model.dto.CandidateDTO;
import project.recruitment.searchOptions.CandidateSearchOptions;
import project.recruitment.service.CandidateService;

import java.time.LocalDate;
import java.util.Optional;

@Configuration
public class StartupConfiguration
{
    @Bean
    CommandLineRunner getRunner(final CandidateService candidateService)
    {
        return args -> {
           // InitializeDatabaseData(candidateService);
        };
    }

    private void InitializeDatabaseData(final CandidateService candidateService)
    {
        // add some data if database is empty
        if(candidateService.getCandidates(CandidateSearchOptions.builder().build(), Optional.of(0), Optional.of(5), null, null).size() == 0)
        {
            candidateService.addCandidate(CandidateDTO
                    .builder()
                    .firstName("pera")
                    .lastName("peric")
                    .email("pera.peric@gmail.com")
                    .contactNumber("065214528")
                    .cityOfLiving("nis")
                    .dateOfBirth(LocalDate.of(1998, 5, 21))
                    .build());

            candidateService.addCandidate(CandidateDTO
                    .builder()
                    .firstName("mika")
                    .lastName("mikic")
                    .email("mika.mikic@gmail.com")
                    .contactNumber("06552654528")
                    .cityOfLiving("belgrade")
                    .dateOfBirth(LocalDate.of(1997, 7, 21))
                    .build());

            candidateService.addCandidate(CandidateDTO
                    .builder()
                    .firstName("sava")
                    .lastName("savic")
                    .email("sava.savic@gmail.com")
                    .contactNumber("065214528")
                    .cityOfLiving("novi sad")
                    .dateOfBirth(LocalDate.of(1990, 5, 10))
                    .build());

            candidateService.addCandidate(CandidateDTO
                    .builder()
                    .firstName("ivko")
                    .lastName("ivkovic")
                    .email("ivko.ivkovic@gmail.com")
                    .contactNumber("06234528")
                    .cityOfLiving("nis")
                    .dateOfBirth(LocalDate.of(1992, 2, 13))
                    .build());

            candidateService.addCandidate(CandidateDTO
                    .builder()
                    .firstName("marko")
                    .lastName("markovic")
                    .email("marko.markovic@gmail.com")
                    .contactNumber("0645896655")
                    .cityOfLiving("nis")
                    .dateOfBirth(LocalDate.of(2000, 4, 24))
                    .build());
        }

    }
}
