package NeuralNetworkLibrary.Activation;

import NeuralNetworkLibrary.Utility.Matrix;

public interface ActivationFunction {

    /**
     * Forward activation: A = f(Z)
     */
    Matrix activate(Matrix z);

    /**
     * Derivative used during backpropagation: f'(Z)
     */
    Matrix derivative(Matrix z);

    /**
     * Name of activation function (for debugging / summary)
     */
    String name();
}
