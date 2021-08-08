package project.recruitment.exception;

public class UserAlreadyExistsException extends RuntimeException
{
    public UserAlreadyExistsException(final String message)
    {
        super(message);
    }
}
