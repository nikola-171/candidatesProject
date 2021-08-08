package project.recruitment.rest.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import project.recruitment.model.dto.user.UserDTO;
import project.recruitment.model.dto.user.UserGetDTO;
import project.recruitment.rest.AppUserController;
import project.recruitment.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AppUserControllerImpl implements AppUserController
{
    private final UserService _userService;

    @Override
    public List<UserGetDTO> getAllUsers() {
        return _userService.getAllUsers();
    }
}
