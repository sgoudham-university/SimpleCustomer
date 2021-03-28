package exception;

public class FileNotReadException extends Exception {
    public FileNotReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
