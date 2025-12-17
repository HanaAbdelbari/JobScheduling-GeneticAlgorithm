package NueralNetworkLibrary.ExceptionHandlers;

// matrix dimensions are not match
public class ShapeMismatchException extends RuntimeException {

    public ShapeMismatchException(String message) {
        super(message);
    }
}