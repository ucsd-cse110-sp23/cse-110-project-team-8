package cse110.middleware;

public class ResponseStrings {
    public static final String USERNAME_TAKEN = "Error: Username already take.n";
    public static final String ADDED_USER = "Success: Added User";
    public static final String LOGIN_SUCCESS = "Success: Username and password match.";
    public static final String USERNAME_ERROR = "Error: This username does not exist.";
    public static final String PASSWORD_ERROR = "Error: Password does not match.";
    public static final String DATABASE_ERROR = "Error: Database could not be connected to.";
    public static final String DATABASE_WRITE_SUCCESS = "Success: Database successfully written to.";
    public static final String DATABASE_READ_SUCCESS = "Success: Database successfully read.";
    public static final String SERVER_ERROR = "Error: Server failed to respond.";
    public static final String SERVER_INVALID_ERROR = "Error: Invalid request to server.";
    public static final String EMAIL_SMTP_ERROR = "Error: Invalid SMTP Host.";
    public static final String EMAIL_SUCCESS = "Success: Email sent.";
}
