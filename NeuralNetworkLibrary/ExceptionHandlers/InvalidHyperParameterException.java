package NeuralNetworkLibrary.ExceptionHandlers;

// hyperparamvalue is invalid (learning rate or batch size)
public class InvalidHyperParameterException extends RuntimeException {

    public InvalidHyperParameterException(String message) {
        super(message);
    }
}

