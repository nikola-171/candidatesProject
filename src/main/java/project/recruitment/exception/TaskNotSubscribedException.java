package project.recruitment.exception;

public class TaskNotSubscribedException extends RuntimeException
{
    public TaskNotSubscribedException(final String message)
    {
        super(message);
    }
}
