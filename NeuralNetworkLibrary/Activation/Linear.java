package NeuralNetworkLibrary.Activation;

import NeuralNetworkLibrary.Utility.Matrix;

public class Linear implements ActivationFunction {

    @Override
    public Matrix activate(Matrix z) {
        return z;
    }

    @Override
    public Matrix derivative(Matrix z) {
        return z.apply(v -> 1.0);
    }

    @Override
    public String name() {
        return "Linear";
    }
}
