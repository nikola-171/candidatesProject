package project.recruitment.model.dto.passwordReset;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetSubmitDTO
{
    @JsonProperty("password")
    String password;

    public String getPassword()
    {
        return password;
    }
}
