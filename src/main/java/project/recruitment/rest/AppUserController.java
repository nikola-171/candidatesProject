package project.recruitment.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.recruitment.model.dto.user.UserGetDTO;

import java.util.List;


@RequestMapping("/users")
public interface AppUserController
{
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("")
    List<UserGetDTO> getAllUsers();
}
