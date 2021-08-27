package project.recruitment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.recruitment.exception.PasswordResetTokenExpiredException;
import project.recruitment.exception.ResourceNotFoundException;
import project.recruitment.model.entity.PasswordResetEntity;
import project.recruitment.repository.PasswordResetRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService
{
    final PasswordResetRepository _passwordResetRepository;
    private final String TOKEN_EXPIRED = "token has expired";

    // add new password reset request to database
    public void addRequest(final UUID token, final Long userId)
    {
        _passwordResetRepository.save(PasswordResetEntity.builder().userToken(token.toString()).userId(userId).build());
    }

    // validate if id and token are in database
    public Boolean validateToken(final UUID token, final Long userId)
    {
        Optional<PasswordResetEntity> entityDb = _passwordResetRepository.findByUserTokenAndUserId(token.toString(), userId);
        if(entityDb.isEmpty())
            return false;

        PasswordResetEntity entity = entityDb.get();
        if(entity.getSubmit())
            throw new PasswordResetTokenExpiredException(TOKEN_EXPIRED);

        return true;
    }

    // set a password request as used
    public void requestSubmit(final UUID token, final Long userId)
    {
        PasswordResetEntity entity = _passwordResetRepository.findByUserTokenAndUserId(token.toString(), userId)
                .orElseThrow(() -> new ResourceNotFoundException(generatePasswordRequestNotFound()));
        entity.setSubmit(true);
        _passwordResetRepository.save(entity);
    }

    private String generatePasswordRequestNotFound()
    {
        return "Password request credentials are not valid";
    }
}
