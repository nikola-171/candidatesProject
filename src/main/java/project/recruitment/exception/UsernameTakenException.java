package project.recruitment.exception;

public class UsernameTakenException extends RuntimeException
{
    public UsernameTakenException(final String message)
    {
        super(message);
    }
}
