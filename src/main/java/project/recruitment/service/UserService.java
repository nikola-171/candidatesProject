package project.recruitment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.recruitment.exception.ResourceNotFoundException;
import project.recruitment.exception.UserAlreadyExistsException;
import project.recruitment.model.Role;
import project.recruitment.model.UserDetailsImpl;
import project.recruitment.model.dto.user.UserDTO;
import project.recruitment.model.dto.user.UserGetDTO;
import project.recruitment.model.entity.CandidateEntity;
import project.recruitment.model.entity.UserEntity;
import project.recruitment.repository.UserRepository;
import project.recruitment.utils.mapper.UserMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService
{
    final UserRepository _usersRepository;
    final CandidateService _candidateService;

    final String INVALID_CREDENTIALS = "Username or password are not valid";

    public List<UserGetDTO> getAllUsers()
    {
        return _usersRepository.findAll().stream().map(UserMapper::toDTO).collect(Collectors.toList());
    }

    public Optional<UserEntity> getUserByUsername(final String username)
    {
        return _usersRepository.findByUsername(username);
    }
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        final Optional<UserEntity> userEntityDb = _usersRepository.findByUsername(s);

        if(userEntityDb.isPresent())
        {
            // user is a regular user or admin
            UserEntity userEntity = userEntityDb.get();
            final List<GrantedAuthority> authorities = userEntity.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.name()))
                    .collect(Collectors.toList());

            return UserDetailsImpl.builder()
                    .username(userEntity.getUsername())
                    .password(userEntity.getPassword())
                    .accountNonExpired(userEntity.isAccountNonExpired())
                    .accountNonLocked(userEntity.isAccountNonLocked())
                    .credentialsNonExpired(userEntity.isCredentialsNonExpired())
                    .enabled(userEntity.isEnabled())
                    .authorities(authorities)
                    .id(userEntity.getId())
                    .build();
        }else{
            final Optional<CandidateEntity> candidateDb = _candidateService.getCandidateByUsername(s);
            if(!candidateDb.isPresent())
            {
                // entity was not found in regular user table or in candidate table
                throw new ResourceNotFoundException(INVALID_CREDENTIALS);
            }
            else
            {
                // user is a candidate
                CandidateEntity candidate = candidateDb.get();
                return UserDetailsImpl.builder()
                        .username(candidate.getUsername())
                        .password(candidate.getPassword())
                        .accountNonExpired(candidate.getActive())
                        .accountNonLocked(candidate.getActive())
                        .credentialsNonExpired(candidate.getActive())
                        .enabled(candidate.getActive())
                        .authorities(Arrays.asList(new SimpleGrantedAuthority(Role.CANDIDATE.name())))
                        .id(candidate.getId())
                        .build();
            }
        }


    }

    public UserEntity addUser(final UserDTO userDTO, final List<Role> roles)
    {
        final String email = userDTO.getEmail();

        if(_usersRepository.findByEmail(email).isPresent())
        {
            throw new UserAlreadyExistsException(generateUserExistsMessage(email));
        }

        final PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        final UserEntity userEntity = UserEntity.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .username(userDTO.getUsername())
                .password(encoder.encode(userDTO.getPassword()))
                .email(email)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .roles(roles)
                .build();

        return _usersRepository.save(userEntity);
    }

    private String generateUserExistsMessage(final String email)
    {
        return String.format("User with email '%s' already exists", email);
    }
}
