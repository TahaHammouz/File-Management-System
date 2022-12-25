package Exception;

public class PostException extends Exception {
    public PostException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();
        return "import Exception: " + message;

    }
}
