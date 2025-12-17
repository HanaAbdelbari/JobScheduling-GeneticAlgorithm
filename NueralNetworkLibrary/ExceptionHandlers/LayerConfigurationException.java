package NeuralNetworkLibrary.ExceptionHandlers;

// these exception is for invalid layer sizes or intialization problem
/*
- numberOfNeurons <= 0
- inputSize <= 0
- activationFunction == null
- inputSize != previousLayerOutputSize
 */


public class LayerConfigurationException extends RuntimeException {

    public LayerConfigurationException(String message) {
        super(message);
    }
}
