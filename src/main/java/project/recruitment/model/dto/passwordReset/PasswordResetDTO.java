package project.recruitment.model.dto.passwordReset;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Builder
@Value
public class PasswordResetDTO
{
    Long userId;

    UUID token;
}
