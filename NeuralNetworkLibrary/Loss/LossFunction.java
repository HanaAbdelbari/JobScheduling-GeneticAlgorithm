package NeuralNetworkLibrary.Loss;

import NeuralNetworkLibrary.Utility.Matrix;

/**
 * Interface for loss functions.
 * Defines loss computation and gradient calculation.
 */
public interface LossFunction {

    /**
     * Compute scalar loss value.
     * @param yTrue True labels
     * @param yPred Predicted outputs
     * @return Loss value
     */
    double loss(Matrix yTrue, Matrix yPred);

    /**
     * Compute gradient of loss w.r.t predictions.
     * @param yTrue True labels
     * @param yPred Predicted outputs
     * @return Gradient matrix
     */
    Matrix derivative(Matrix yTrue, Matrix yPred);

    /**
     * Name of loss function (for debugging/logging)
     */
    String name();
}
