package project.recruitment.model.dto.user;

import lombok.Builder;
import lombok.Value;
import project.recruitment.model.Role;

import java.util.List;

@Value
@Builder
public class UserGetDTO
{
    Long id;
    String firstName;
    String lastName;
    String email;
    String username;
    List<Role> roles;
}
