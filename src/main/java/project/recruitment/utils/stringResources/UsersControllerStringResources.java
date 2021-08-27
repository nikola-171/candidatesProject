package project.recruitment.utils.stringResources;

public class UsersControllerStringResources
{
    // strings for logger
    public static String getUsers = "[%s, ROLE(s) - %s] - get list of users";
    public static String addUser = "[%s, ROLE(s) - %s] - add user [user - %s]";
    public static String getUserById = "[%s, ROLE(s) - %s] - get user by id [id - %s]";
    public static String deleteUser = "[%s, ROLE(s) - %s] - delete user by id [id - %s]";
    public static String updateUser = "[%s, ROLE(s) - %s] - edit user [user id - %s, values - (%s)]";
    public static String deactivateUser = "[%s, ROLE(s) - %s] - deactivate user [user id - %s]";
    public static String activateUser = "[%s, ROLE(s) - %s] - activate user [user id - %s]";
    public static String passwordResetRequest = "password reset request - %s";
    public static String passwordResetRequestSuccess = "password reset request success - %s";
    public static String passwordResetRequestFail = "password reset request failed - %s";
    public static String passwordResetSubmit = "password reset - [id - %s, token - %s]";
    public static String passwordResetSuccess = "password reset - successfully reseted password [id - %s, token - %s]";
    public static String passwordResetFail = "password reset - invalid password reset credentials - [id - %s, token - %s]";

    // messages for password reset
    public static String operationUnavailable = "operation is currently unavailable, please try again later";
    public static String emailSent = "email for password reset successfully sent";
    public static String passwordUpdateSuccess = "password successfully updated";
    public static String passwordUpdateFail = "invalid password reset credentials";
}
