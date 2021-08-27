package project.recruitment.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetRequestDTO
{
    @JsonProperty("username")
    String username;

    public String getUsername()
    {
        return username;
    }
}
