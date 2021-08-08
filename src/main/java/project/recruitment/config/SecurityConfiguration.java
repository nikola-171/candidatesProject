package project.recruitment.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.recruitment.model.Role;
import project.recruitment.service.UserService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
    private final ObjectMapper _mapper;
    private final UserService _userService;

    final PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(_userService)
                .passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.csrf().disable()
                .exceptionHandling()
                .accessDeniedHandler((request, response, e) -> {
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.setContentType("Application/json");

                    final String path = request.getServletPath();

                    final Problem problem = Problem.create().withStatus(HttpStatus.FORBIDDEN)
                            .withTitle("You do not have rights to access the required resources: " + path)
                            .withDetail(e.getMessage());

                    response.getOutputStream().println(_mapper.writeValueAsString(problem));
                })
                .and()
                .httpBasic()
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.setContentType("Application/json");

                    final Problem problem = Problem.create().withStatus(HttpStatus.UNAUTHORIZED)
                            .withTitle("Authentication failed")
                            .withDetail(authException.getMessage());

                    response.getOutputStream().println(_mapper.writeValueAsString(problem));

                });
    }
}
