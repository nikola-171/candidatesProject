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
        DATA_VALIDATION
    }
}