package project.recruitment.rest.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.recruitment.model.Role;
import project.recruitment.model.UserDetailsImpl;
import project.recruitment.model.dto.PasswordResetRequestDTO;
import project.recruitment.model.dto.passwordReset.PasswordResetSubmitDTO;
import project.recruitment.model.dto.user.UserDTO;
import project.recruitment.model.dto.user.UserGetDTO;
import project.recruitment.rest.AppUserController;
import project.recruitment.searchOptions.UserSearchOptions;
import project.recruitment.service.PasswordResetService;
import project.recruitment.service.UserService;
import project.recruitment.utils.Logger;
import project.recruitment.utils.mapper.UserMapper;
import project.recruitment.utils.stringResources.UsersControllerStringResources;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AppUserControllerImpl implements AppUserController
{
    private final UserService _userService;
    private final PasswordResetService _passwordResetService;
    private final Logger _logger;

    private UserDetailsImpl getLoggedUserDetails()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ((UserDetailsImpl) auth.getPrincipal());
    }

    @Override
    public List<UserGetDTO> getUsers(@RequestParam(value = "firstName", required = false, defaultValue = "") final String firstName,
                                        @RequestParam(value = "lastName", required = false, defaultValue = "") final String lastName,
                                        @RequestParam(value = "username", required = false, defaultValue = "") final String username,
                                        @RequestParam(value = "email", required = false, defaultValue = "") final String email,
                                        @RequestParam(value = "currentPage", required = false, defaultValue = "0") final Optional<Integer> currentPage,
                                        @RequestParam(value = "itemsPerPage", required = false, defaultValue = "5") final Optional<Integer> itemsPerPage,
                                        @RequestParam(value = "orderDirection", required = false, defaultValue = "") final String orderDirection,
                                        @RequestParam(value = "orderColumn", required = false, defaultValue = "") final String orderColumn)
    {
        UserDetailsImpl user = getLoggedUserDetails();
        _logger.logInfo(String.format(UsersControllerStringResources.getUsers, user.getUsername(), user.getAuthorities().toString()));

        return _userService.getAllUsers(UserSearchOptions.builder()
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .email(email)
                .build(), currentPage, itemsPerPage, orderDirection, orderColumn);
    }

    @Override
    public UserGetDTO addUser(UserDTO userDTO)
    {
        UserDetailsImpl user = getLoggedUserDetails();
        _logger.logInfo(String.format(UsersControllerStringResources.addUser, userDTO.getUsername(), user.getAuthorities().toString(), userDTO.toString()));
        return UserMapper.toDTO(_userService.addUser(userDTO, Arrays.asList(Role.USER)));
    }

    @Override
    public UserGetDTO getUserById(final Long id)
    {
        UserDetailsImpl user = getLoggedUserDetails();
        _logger.logInfo(String.format(UsersControllerStringResources.getUserById, user.getUsername(), user.getAuthorities().toString(), id.toString()));
        return _userService.getUserById(id);
    }

    @Override
    public ResponseEntity<?> deleteUser(Long id)
    {
        UserDetailsImpl user = getLoggedUserDetails();
        _logger.logInfo(String.format(UsersControllerStringResources.deleteUser, user.getUsername(), user.getAuthorities().toString(), id.toString()));
        _userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> updateUser(Long id, UserDTO userDTO)
    {
        UserDetailsImpl user = getLoggedUserDetails();
        _logger.logInfo(String.format(UsersControllerStringResources.updateUser, user.getUsername(), user.getAuthorities().toString(), id.toString(), userDTO.toString()));
        return ResponseEntity.ok(_userService.editUser(id, userDTO));
    }

    @Override
    public ResponseEntity<?> deactivateUser(Long id)
    {
        UserDetailsImpl user = getLoggedUserDetails();
        _logger.logInfo(String.format(UsersControllerStringResources.deactivateUser, user.getUsername(), user.getAuthorities().toString(), id.toString()));
        _userService.deactivateUser(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> activateUser(Long id)
    {
        UserDetailsImpl user = getLoggedUserDetails();
        _logger.logInfo(String.format(UsersControllerStringResources.activateUser, user.getUsername(), user.getAuthorities().toString(), id.toString()));
        _userService.activateUser(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> passwordResetRequest(PasswordResetRequestDTO passwordResetRequestDTO)
    {
        _logger.logInfo(String.format(UsersControllerStringResources.passwordResetRequest, passwordResetRequestDTO.getUsername()));

        Boolean success = _userService.resetPassword(passwordResetRequestDTO);
        if(!success)
        {
            _logger.logInfo(String.format(UsersControllerStringResources.passwordResetRequestFail, passwordResetRequestDTO.getUsername()));
            return ResponseEntity.ok().body(UsersControllerStringResources.operationUnavailable);
        }
        _logger.logInfo(String.format(UsersControllerStringResources.passwordResetRequestSuccess, passwordResetRequestDTO.getUsername()));
        return ResponseEntity.ok(UsersControllerStringResources.emailSent);
    }

    @Override
    public ResponseEntity<?> passwordReset(Long id, UUID token, PasswordResetSubmitDTO passwordResetSubmitDTO)
    {
        _logger.logInfo(String.format(UsersControllerStringResources.passwordResetSubmit, id.toString(), token.toString()));

        if(_passwordResetService.validateToken(token, id))
        {
            _passwordResetService.requestSubmit(token, id);
            _userService.resetPassword(id, passwordResetSubmitDTO.getPassword());
            _logger.logInfo(String.format(UsersControllerStringResources.passwordResetSuccess, id.toString(), token.toString()));

            return ResponseEntity.ok(UsersControllerStringResources.passwordUpdateSuccess);
        }
        _logger.logDanger(String.format(UsersControllerStringResources.passwordResetFail, id.toString(), token.toString()));

        return ResponseEntity.badRequest().body(UsersControllerStringResources.passwordUpdateFail);
    }
}
