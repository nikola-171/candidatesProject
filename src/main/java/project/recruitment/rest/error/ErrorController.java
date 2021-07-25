package project.recruitment.rest.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import project.recruitment.exception.CandidateActivationException;
import project.recruitment.exception.ResourceNotFoundException;
import project.recruitment.exception.error.ApiError;

import java.sql.SQLException;

@ControllerAdvice
@ResponseBody
public class ErrorController
{
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ApiError handleEmployeeNotFound(final ResourceNotFoundException resourceNotFoundException)
    {
        return ApiError.builder()
                .code(ApiError.ErrorCode.CANDIDATE_NOT_FOUND)
                .message(resourceNotFoundException.getMessage())
                .build();
    }

    @ExceptionHandler(CandidateActivationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ApiError handleEmployeeNotFound(final CandidateActivationException candidateActivationException)
    {
        return ApiError.builder()
                .code(ApiError.ErrorCode.CANDIDATE_ACTIVATION_ERROR)
                .message(candidateActivationException.getMessage())
                .build();
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ApiError handleSqlException(final SQLException sqlException)
    {
        return ApiError.builder()
                .code(ApiError.ErrorCode.DATA_VALIDATION)
                .message("OPERATION COULD NOT BE COMPLETED")
                .build();
    }

    
}
