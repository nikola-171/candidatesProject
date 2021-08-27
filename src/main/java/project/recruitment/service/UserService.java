package project.recruitment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import project.recruitment.exception.ResourceNotFoundException;
import project.recruitment.exception.UserAlreadyExistsException;
import project.recruitment.exception.UserActivationException;
import project.recruitment.model.Role;
import project.recruitment.model.UserDetailsImpl;
import project.recruitment.model.dto.PasswordResetRequestDTO;
import project.recruitment.model.dto.user.UserDTO;
import project.recruitment.model.dto.user.UserGetDTO;
import project.recruitment.model.entity.CandidateEntity;
import project.recruitment.model.entity.UserEntity;
import project.recruitment.repository.UserRepository;
import project.recruitment.repository.specification.UserSearchSpecification;
import project.recruitment.rest.impl.AppUserControllerImpl;
import project.recruitment.rest.impl.CandidatesControllerImpl;
import project.recruitment.searchOptions.UserSearchOptions;
import project.recruitment.utils.EmailSender;
import project.recruitment.utils.emailTemplate.EmailTemplate;
import project.recruitment.utils.mapper.UserMapper;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService
{
    final UserRepository _usersRepository;
    final CandidateService _candidateService;
    final PasswordResetService _passwordResetService;
    final String INVALID_CREDENTIALS = "Username or password are not valid";

    // activate user
    public void activateUser(final Long userId)
    {
        UserEntity user = getUserFromDatabase(userId);

        if(user.isAccountNonLocked())
        {
            // user is already active
            throw new UserActivationException(generateUserActivationExceptionMessage(userId, true));

        }
        user.setAccountNonLocked(true);
        _usersRepository.save(user);
    }

    // deactivate user
    public void deactivateUser(final Long userId)
    {
        UserEntity user = getUserFromDatabase(userId);
        if(!user.isAccountNonLocked())
        {
            // user is already deactivated
            throw new UserActivationException(generateUserActivationExceptionMessage(userId,false));
        }
        user.setAccountNonLocked(false);
        _usersRepository.save(user);
    }

    //edit user
    public UserGetDTO editUser(final Long userId, final UserDTO userDTO)
    {
        UserEntity user = getUserFromDatabase(userId);
        EditUser(user, userDTO);

        return UserMapper.toDTO(_usersRepository.save(user));
    }

    //delete user
    public void deleteUser(final Long id)
    {
        UserEntity user = getUserFromDatabase(id);

        user.setDeleteDate(ZonedDateTime.now());
        _usersRepository.save(user);
    }

    //get user
    @Transactional
    public UserGetDTO getUserById(final Long id)
    {
        UserEntity user = getUserFromDatabase(id);

        return UserMapper.toDTO(user);
    }

    // get users
    @Transactional
    public List<UserGetDTO> getAllUsers(final UserSearchOptions userSearchOptions,
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
                case "username" -> Sort.by("username");
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

        return _usersRepository.findAll(new UserSearchSpecification(userSearchOptions), request)
                .stream().map(UserMapper::toDTO).collect(Collectors.toList());
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
            if(candidateDb.isEmpty())
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

    // reset password request
    public Boolean resetPassword(final PasswordResetRequestDTO passwordResetRequestDTO) {
        String username = passwordResetRequestDTO.getUsername();
        Optional<UserEntity> user = getUserByUsername(username);

        if(user.isEmpty())
        {
            throw new ResourceNotFoundException(generateUserNotFoundMessage(username));
        }
        UserEntity entity = user.get();
        UUID token = UUID.randomUUID();
        String url = linkTo(methodOn(AppUserControllerImpl.class).passwordReset(entity.getId(), token, null)).toString();
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
        UserEntity user = getUserFromDatabase(id);
        user.setPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(newPassword));
        _usersRepository.save(user);
    }

    public UserEntity addUser(final UserDTO userDTO, final List<Role> roles)
    {
        final String username = userDTO.getUsername();

        if(_usersRepository.findByUsername(username).isPresent())
        {
            throw new UserAlreadyExistsException(generateUserExistsMessage(username));
        }

        final PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        final UserEntity userEntity = UserEntity.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .username(username)
                .password(encoder.encode(userDTO.getPassword()))
                .email(userDTO.getEmail())
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

    private String generateUserNotFoundMessage(final Long id)
    {
        return String.format("User with id '%s' is not found", id.toString());
    }

    private String generateUserNotFoundMessage(final String username)
    {
        return String.format("User with username '%s' is not found", username);
    }

    private UserEntity getUserFromDatabase(final Long id)
    {
        return _usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(generateUserNotFoundMessage(id)));
    }

    private void EditUser(UserEntity user, UserDTO userDTO)
    {
        String firstName = userDTO.getFirstName();

        if(firstName != null)
        {
            firstName = firstName.strip().replaceAll(" +", " ");
        }
        if(StringUtils.hasText(firstName) && !user.getFirstName().equals(firstName))
        {
            user.setFirstName(firstName);
        }

        String lastName = userDTO.getLastName();

        if(lastName != null)
        {
            lastName = lastName.strip().replaceAll(" +", " ");
        }
        if(StringUtils.hasText(lastName) && !user.getLastName().equals(lastName))
        {
            user.setLastName(lastName);
        }

        String username = userDTO.getUsername();

        if(username != null)
        {
            username = username.strip().replaceAll(" +", " ");
        }
        if(StringUtils.hasText(username) && !user.getUsername().equals(username))
        {
            user.setUsername(username);
        }

        String email = userDTO.getEmail();

        if(email != null)
        {
            email = email.strip().replaceAll(" +", "");
        }
        if(StringUtils.hasText(email) && !user.getEmail().equals(email))
        {
            user.setEmail(email);
        }

        String password = userDTO.getPassword();

        if(password != null)
        {
            password = password.strip().replaceAll(" +", "");
        }
        if(StringUtils.hasText(password))
        {
            user.setPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(password));
        }
    }

    private String generateUserActivationExceptionMessage(final Long id, final boolean status)
    {
        String message = status ? "activated" : "deactivated";
        return String.format("User with id '%s' is already '%s'", id.toString(), message);
    }
}
