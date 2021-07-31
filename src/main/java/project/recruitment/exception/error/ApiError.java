package project.recruitment.exception.error;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ApiError
{
    String message;
    ErrorCode code;

    public enum ErrorCode
    {
        CANDIDATE_NOT_FOUND,
        CANDIDATE_ACTIVATION_ERROR,
        DATA_VALIDATION,
        SERVICE_UNAVAILABLE,
        TASK_NOT_FOUND,
        RESOURCE_NOT_FOUND,
        SOLUTION_ALREADY_EXISTS,
        TASK_NOT_SUBSCRIBED,
        TASK_ALREADY_RATED
    }
}
