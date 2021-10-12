package exception;

public class WrongEmailException extends Exception{
    public WrongEmailException(String message) {
        super(message);
    }
}