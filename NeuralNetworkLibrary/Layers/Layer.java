package NeuralNetworkLibrary.Layers;

import NeuralNetworkLibrary.Activation.ActivationFunction;
import NeuralNetworkLibrary.Initialization.Initializer;
import NeuralNetworkLibrary.Utility.Matrix;

/**
 * Base class for neural network layers.
 * Implements forward and backward propagation using Matrix abstraction.
 */
public class Layer {

    protected int nInputs;
    protected int nNeurons;
    protected ActivationFunction activation;

    protected Matrix weights;
    protected Matrix biases;

    // Cached values for backpropagation
    protected Matrix inputs;
    protected Matrix z;
    protected Matrix a;

    public Layer(int nInputs,
                 int nNeurons,
                 ActivationFunction activation,
                 Initializer initializer) {

        if (nInputs <= 0 || nNeurons <= 0) {
            throw new IllegalArgumentException("Layer dimensions must be positive.");
        }
        if (activation == null || initializer == null) {
            throw new IllegalArgumentException("Activation and initializer cannot be null.");
        }

        this.nInputs = nInputs;
        this.nNeurons = nNeurons;
        this.activation = activation;

        this.weights = new Matrix(initializer.initialize(nInputs, nNeurons));
        this.biases = new Matrix(1, nNeurons); // initialized to zeros
    }

    /**
     * Forward propagation.
     * Z = XW + b
     * A = activation(Z)
     */
    public Matrix forward(Matrix inputs) {
        this.inputs = inputs;
        this.z = inputs.dot(weights).add(biases);
        this.a = activation.activate(z);
        return a;
    }

    /**
     * Backward propagation.
     * Updates weights and biases using gradient descent.
     */
    public Matrix backward(Matrix dOut, double learningRate) {

        // dZ = dOut ⊙ activation'(Z)
        Matrix dZ = dOut.multiply(activation.derivative(z));

        // Gradients
        Matrix dWeights = inputs.transpose().dot(dZ);
        Matrix dBiases = dZ.sumRows();

        // Gradient for previous layer
        Matrix dInputs = dZ.dot(weights.transpose());

        // Update parameters
        weights = weights.subtract(dWeights.scale(learningRate));
        biases = biases.subtract(dBiases.scale(learningRate));

        return dInputs;
    }

    public int getNeurons() {
        return nNeurons;
    }

    public int getInputs() {
        return nInputs;
    }

    public Matrix getWeights() {
        return weights;
    }

    public Matrix getBiases() {
        return biases;
    }

    @Override
    public String toString() {
        return String.format(
                "Layer(inputs=%d, neurons=%d, activation=%s)",
                nInputs, nNeurons, activation.name()
        );
    }
}
