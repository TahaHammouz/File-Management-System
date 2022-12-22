package Exception;

public class AuthException extends Exception{
    public AuthException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();
        return "AuthException: " + message;

    }
}
