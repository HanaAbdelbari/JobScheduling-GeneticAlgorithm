package NueralNetworkLibrary.Layers;

import NueralNetworkLibrary.Activation.ActivationFunction;
import NueralNetworkLibrary.Initialization.Initializer;


public class Layer {
    protected int nInputs;
    protected int nNeurons;
    protected ActivationFunction activation;

    protected double[][] weights;
    protected double[][] biases;   //[1 x nNeurons]

    // Cache for backpropagation
    protected double[][] z;       // Pre-activation values
    protected double[][] a;       // Post-activation values (output)
    protected double[][] inputs;


    public Layer(int nInputs, int nNeurons, ActivationFunction activation,
                 Initializer initializer) {
        this.nInputs = nInputs;
        this.nNeurons = nNeurons;
        this.activation = activation;

        this.weights = initializer.initialize(nInputs, nNeurons);

        this.biases = new double[1][nNeurons];
        for (int j = 0; j < nNeurons; j++) {
            this.biases[0][j] = 0.0;
        }
    }


    public double[][] forward(double[][] inputs) {
        this.inputs = inputs;

        //z = inputs · weights + biases
        this.z = matrixMultiply(inputs, weights);
        addBiases(this.z, biases);

        this.a = activation.activate(z);

        return this.a;
    }


    public double[][] backward(double[][] dOut, double learningRate) {


        // d_z = d_out * activation'(z)
        double[][] activationDerivative = activation.derivative(z);
        double[][] dZ = elementWiseMultiply(dOut, activationDerivative);

        // Compute gradients
        // d_weights = inputs^T · d_z
        double[][] dWeights = matrixMultiply(transpose(inputs), dZ);

        // d_biases = sum(d_z) across batch
        double[][] dBiases = sumRows(dZ);

        // d_inputs = d_z · weights^T (for previous layer)
        double[][] dInputs = matrixMultiply(dZ, transpose(weights));

        updateWeights(weights, dWeights, learningRate);
        updateWeights(biases, dBiases, learningRate);

        return dInputs;
    }

    public int getNeurons() {
        return nNeurons;
    }

    public int getInputs() {
        return nInputs;
    }


    public double[][] getWeights() {
        return weights;
    }

    public double[][] getBiases() {
        return biases;
    }


    protected double[][] matrixMultiply(double[][] a, double[][] b) {
        int rows = a.length;
        int cols = b[0].length;
        int common = a[0].length;
        double[][] result = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = 0.0;
                for (int k = 0; k < common; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return result;
    }

    protected void addBiases(double[][] matrix, double[][] biases) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] += biases[0][j];
            }
        }
    }

    protected double[][] elementWiseMultiply(double[][] a, double[][] b) {
        double[][] result = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                result[i][j] = a[i][j] * b[i][j];
            }
        }
        return result;
    }

    //Matrix transpose: B = A^T
    protected double[][] transpose(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        double[][] result = new double[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[j][i] = matrix[i][j];
            }
        }
        return result;
    }


    protected double[][] sumRows(double[][] matrix) {
        int cols = matrix[0].length;
        double[][] result = new double[1][cols];

        for (int j = 0; j < cols; j++) {
            result[0][j] = 0.0;
            for (int i = 0; i < matrix.length; i++) {
                result[0][j] += matrix[i][j];
            }
        }
        return result;
    }

    protected void updateWeights(double[][] weights, double[][] gradients,
                                 double learningRate) {

        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[0].length; j++) {
                weights[i][j] -= learningRate * gradients[i][j];

            }
        }
    }
}
