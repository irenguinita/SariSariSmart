package Database.CustomException;

public class SignUpFailedException extends RuntimeException {
    public SignUpFailedException(String message) {
        super(message);
    }
}
