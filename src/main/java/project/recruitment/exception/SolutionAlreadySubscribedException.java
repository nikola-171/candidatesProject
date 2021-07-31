package project.recruitment.exception;

public class SolutionAlreadySubscribedException extends RuntimeException
{
    public SolutionAlreadySubscribedException(final String message)
    {
        super(message);
    }
}
