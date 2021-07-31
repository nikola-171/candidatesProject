package project.recruitment.exception;

public class TaskAlreadyRatedException extends RuntimeException
{
    public TaskAlreadyRatedException(final String message)
    {
        super(message);
    }
}
