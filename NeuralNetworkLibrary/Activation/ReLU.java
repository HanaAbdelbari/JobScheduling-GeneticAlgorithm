package NeuralNetworkLibrary.Activation;

import NeuralNetworkLibrary.Utility.Matrix;

public class ReLU implements ActivationFunction {

    @Override
    public Matrix activate(Matrix z) {
        return z.apply(v -> Math.max(0, v));
    }

    @Override
    public Matrix derivative(Matrix z) {
        return z.apply(v -> v > 0 ? 1.0 : 0.0);
    }

    @Override
    public String name() {
        return "ReLU";
    }
}
