package project.recruitment.rest.error;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import project.recruitment.exception.*;
import project.recruitment.exception.error.ApiError;
import project.recruitment.exception.UserActivationException;
import project.recruitment.utils.Logger;

import java.sql.SQLException;

@ControllerAdvice
@ResponseBody
@RequiredArgsConstructor
public class ErrorController
{
    private final Logger _logger;

    @ExceptionHandler(PasswordResetTokenExpiredException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ApiError handlePasswordResetTokenExpired(final PasswordResetTokenExpiredException passwordResetTokenExpiredException)
    {
        String message = passwordResetTokenExpiredException.getMessage();
        _logger.logError(String.format("error - handle password reset token expired - %s", message));
        return ApiError.builder()
                .code(ApiError.ErrorCode.TOKEN_EXPIRED)
                .message(message)
                .build();
    }

    @ExceptionHandler(UserActivationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ApiError handleUserActivation(final UserActivationException userActivationException)
    {
        String message = userActivationException.getMessage();
        _logger.logError(String.format("error - handle user activation - %s", message));
        return ApiError.builder()
                .code(ApiError.ErrorCode.USER_ACTIVATION_ERROR)
                .message(message)
                .build();
    }

    @ExceptionHandler(UsernameTakenException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ApiError handleUsernameAlreadyTaken(final UsernameTakenException usernameTakenException)
    {
        String message = usernameTakenException.getMessage();
        _logger.logError(String.format("error - handle username already taken - %s", message));
        return ApiError.builder()
                .code(ApiError.ErrorCode.USERNAME_ALREADY_TAKEN)
                .message(message)
                .build();
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ApiError handleUserAlreadyExists(final UserAlreadyExistsException userAlreadyExistsException)
    {
        String message = userAlreadyExistsException.getMessage();
        _logger.logError(String.format("error - handle user already exists - %s", message));
        return ApiError.builder()
                .code(ApiError.ErrorCode.USER_ALREADY_EXISTS)
                .message(message)
                .build();
    }

    @ExceptionHandler(ReviewRangeException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ApiError handleReviewRangeInvalid(final ReviewRangeException reviewRangeException)
    {
        String message = reviewRangeException.getMessage();
        _logger.logError(String.format("error - handle review range is invalid - %s", message));
        return ApiError.builder()
                .code(ApiError.ErrorCode.REVIEW_RANGE_INVALID)
                .message(message)
                .build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ApiError handleEmployeeNotFound(final ResourceNotFoundException resourceNotFoundException)
    {
        String message = resourceNotFoundException.getMessage();
        _logger.logError(String.format("error - handle employee not found - %s", message));
        return ApiError.builder()
                .code(ApiError.ErrorCode.RESOURCE_NOT_FOUND)
                .message(message)
                .build();
    }

    @ExceptionHandler(CandidateActivationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ApiError handleEmployeeNotFound(final CandidateActivationException candidateActivationException)
    {
        String message = candidateActivationException.getMessage();
        _logger.logError(String.format("error - handle employee not found - %s", message));
        return ApiError.builder()
                .code(ApiError.ErrorCode.CANDIDATE_ACTIVATION_ERROR)
                .message(message)
                .build();
    }

    @ExceptionHandler(SolutionAlreadySubscribedException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ApiError handleMultipleSubscribeOnTask(final SolutionAlreadySubscribedException solutionAlreadySubscribedException)
    {
        String message = solutionAlreadySubscribedException.getMessage();
        _logger.logError(String.format("error - handle multiple subscriptions on task - %s", message));
        return ApiError.builder()
                .code(ApiError.ErrorCode.SOLUTION_ALREADY_EXISTS)
                .message(message)
                .build();
    }

    @ExceptionHandler(TaskNotSubscribedException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ApiError handleTaskNotSubscribed(final TaskNotSubscribedException taskNotSubscribedException)
    {
        String message = taskNotSubscribedException.getMessage();
        _logger.logError(String.format("error - handle task not subscribed - %s", message));
        return ApiError.builder()
                .code(ApiError.ErrorCode.TASK_NOT_SUBSCRIBED)
                .message(message)
                .build();
    }

    @ExceptionHandler(TaskAlreadyRatedException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ApiError handleTaskAlreadyRated(final TaskAlreadyRatedException taskAlreadyRatedException)
    {
        String message = taskAlreadyRatedException.getMessage();
        _logger.logError(String.format("error - handle task already rated - %s", message));
        return ApiError.builder()
                .code(ApiError.ErrorCode.TASK_ALREADY_RATED)
                .message(message)
                .build();
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ApiError handleSqlException(final SQLException sqlException)
    {
        String message = sqlException.getMessage();
        _logger.logError(String.format("error - handle SQL exception - %s", message));
        return ApiError.builder()
                .code(ApiError.ErrorCode.DATA_VALIDATION)
                .message("OPERATION COULD NOT BE COMPLETED")
                .build();
    }

    
}
