package project.recruitment.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.recruitment.model.Role;
import project.recruitment.model.dto.user.UserDTO;
import project.recruitment.repository.UserRepository;
import project.recruitment.service.UserService;

import java.util.Arrays;

@Configuration
public class StartupConfiguration
{
    @Bean
    CommandLineRunner getRunner(final UserRepository _userRepository, final UserService _userService)
    {
        return args -> addUsers(_userRepository, _userService);
    }

    private void addUsers(final UserRepository _userRepository, final UserService _userService)
    {
        if(_userRepository.findAll().isEmpty())
        {
            final UserDTO userAdmin = UserDTO.builder()
                    .username("admin")
                    .password("admin")
                    .firstName("john")
                    .lastName("smith")
                    .email("john.smith@test.com")
                    .build();
            _userService.addUser(userAdmin, Arrays.asList(Role.USER, Role.ADMIN));
        }
    }
}
