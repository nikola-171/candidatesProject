package project.recruitment.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.recruitment.model.dto.PasswordResetRequestDTO;
import project.recruitment.model.dto.passwordReset.PasswordResetSubmitDTO;
import project.recruitment.model.dto.user.UserDTO;
import project.recruitment.model.dto.user.UserGetDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RequestMapping("/users")
public interface AppUserController
{
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("")
    @SecurityRequirement(name = "basicSecurity")
    List<UserGetDTO> getUsers(@RequestParam("firstName") final String firstName,
                                 @RequestParam("lastName") final String lastName,
                                 @RequestParam("username") final String username,
                                 @RequestParam("email") final String email,
                                 @RequestParam("currentPage") final Optional<Integer> currentPage,
                                 @RequestParam("itemsPerPage") final Optional<Integer> itemsPerPage,
                                 @RequestParam("orderDirection") final String orderDirection,
                                 @RequestParam("orderColumn") final String orderColumn);

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("")
    @SecurityRequirement(name = "basicSecurity")
    UserGetDTO addUser(@RequestBody final UserDTO userDTO);

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    @SecurityRequirement(name = "basicSecurity")
    UserGetDTO getUserById(@PathVariable final Long id);

    @PreAuthorize("hasAuthority('ADMIN') and #id != authentication.principal.id")
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "basicSecurity")
    ResponseEntity<?> deleteUser(@PathVariable final Long id);

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    @SecurityRequirement(name = "basicSecurity")
    ResponseEntity<?> updateUser(@PathVariable final Long id, @RequestBody UserDTO userDTO);

    @PreAuthorize("hasAuthority('ADMIN') and #id != authentication.principal.id")
    @PostMapping("/{id}/deactivate")
    @SecurityRequirement(name = "basicSecurity")
    ResponseEntity<?> deactivateUser(@PathVariable final Long id);

    @PreAuthorize("hasAuthority('ADMIN') and #id != authentication.principal.id")
    @PostMapping("/{id}/activate")
    @SecurityRequirement(name = "basicSecurity")
    ResponseEntity<?> activateUser(@PathVariable final Long id);

    @PostMapping("/reset-password-request")
    ResponseEntity<?> passwordResetRequest(@RequestBody final PasswordResetRequestDTO passwordResetRequestDTO);

    @PostMapping("/reset-password")
    ResponseEntity<?> passwordReset(@RequestParam("id") final Long id,
                                    @RequestParam("token") final UUID token,
                                    @RequestBody final PasswordResetSubmitDTO passwordResetSubmitDTO);

}
