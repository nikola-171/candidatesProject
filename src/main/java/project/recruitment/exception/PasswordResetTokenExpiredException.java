package project.recruitment.exception;

public class PasswordResetTokenExpiredException extends  RuntimeException
{
    public PasswordResetTokenExpiredException(final String message)
    {
        super(message);
    }
}
