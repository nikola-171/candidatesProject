package project.recruitment.model.dto.user;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserGetDTO
{
    String firstName;
    String lastName;
    String email;
    String username;
}
