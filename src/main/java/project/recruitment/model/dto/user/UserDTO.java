package project.recruitment.model.dto.user;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDTO
{
    @Override
    public String toString() {
        return "UserDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    String firstName;
    String lastName;
    String email;
    String username;
    String password;
}
