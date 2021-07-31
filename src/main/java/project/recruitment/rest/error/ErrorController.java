package project.recruitment.rest.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import project.recruitment.exception.*;
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
                .code(ApiError.ErrorCode.RESOURCE_NOT_FOUND)
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

    @ExceptionHandler(SolutionAlreadySubscribedException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ApiError handleMultipleSubscribeOnTask(final SolutionAlreadySubscribedException solutionAlreadySubscribedException)
    {
        return ApiError.builder()
                .code(ApiError.ErrorCode.SOLUTION_ALREADY_EXISTS)
                .message(solutionAlreadySubscribedException.getMessage())
                .build();
    }

    @ExceptionHandler(TaskNotSubscribedException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ApiError handleTaskNotSubscribed(final TaskNotSubscribedException taskNotSubscribedException)
    {
        return ApiError.builder()
                .code(ApiError.ErrorCode.TASK_NOT_SUBSCRIBED)
                .message(taskNotSubscribedException.getMessage())
                .build();
    }

    @ExceptionHandler(TaskAlreadyRatedException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ApiError handleTaskAlreadyRated(final TaskAlreadyRatedException taskAlreadyRatedException)
    {
        return ApiError.builder()
                .code(ApiError.ErrorCode.TASK_ALREADY_RATED)
                .message(taskAlreadyRatedException.getMessage())
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
