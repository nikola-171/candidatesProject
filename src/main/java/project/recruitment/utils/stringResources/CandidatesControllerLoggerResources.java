package project.recruitment.utils.stringResources;

public class CandidatesControllerLoggerResources
{
    // string resources used in candidates controller
    public static String GetCandidates  = "[%s, ROLE(s) - %s] - get list of candidates";
    public static String GetCandidate  = "[%s, ROLE(s) - %s] - get candidate by id [id - %s]";
    public static String DeleteCandidate  = "[%s, ROLE(s) - %s] - delete candidate by id [id - %s]";
    public static String AddCandidate  = "[%s, ROLE(s) - %s] - add candidate [candidate - %s]";
    public static String ActivateCandidate  = "[%s, ROLE(s) - %s] - activate candidate [candidate id - %s]";
    public static String DeactivateCandidate  = "[%s, ROLE(s) - %s] - deactivate candidate [candidate id - %s]";
    public static String EditCandidate  = "[%s, ROLE(s) - %s] - edit candidate [candidate id - %s, values - (%s)]";
    public static String AddTask  = "[%s, ROLE(s) - %s] - add task to candidate [candidate id - %s, task - (%s)]";
    public static String GetTaskFromCandidate  = "[%s, ROLE(s) - %s] - get task from candidate [candidate id - %s, task id - %s]";
    public static String SubscribeTaskSolution  = "[%s, ROLE(s) - %s] - subscribe solution [candidate id - %s, task id - %s, solution - %s]";
    public static String ReviewTaskSolution  = "[%s, ROLE(s) - %s] - review task solution [candidate id - %s, task id - %s, review - %s]";
    public static String PasswordResetRequest  = "password reset request - %s";
    public static String PasswordResetSubmit  = "password reset - [id - %s, token - %s]";
    public static String PasswordResetSuccess  = "password reset - successfully updated password [id - %s, token - %s]";
    public static String PasswordResetFail  = "password reset - invalid password reset credentials - [id - %s, token - %s]";

    // messages for password reset
    public static String operationUnavailable = "operation is currently unavailable, please try again later";
    public static String emailSent = "email for password reset successfully sent";
    public static String passwordUpdateSuccess = "password successfully updated";
    public static String passwordUpdateFail = "invalid password reset credentials";
}
