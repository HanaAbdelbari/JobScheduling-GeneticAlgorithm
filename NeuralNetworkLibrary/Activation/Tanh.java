package NeuralNetworkLibrary.Activation;

import NeuralNetworkLibrary.Utility.Matrix;

public class Tanh implements ActivationFunction {

    @Override
    public Matrix activate(Matrix z) {
        return z.apply(Math::tanh);
    }

    @Override
    public Matrix derivative(Matrix z) {
        return z.apply(v -> {
            double t = Math.tanh(v);
            return 1.0 - t * t;
        });
    }

    @Override
    public String name() {
        return "Tanh";
    }
}
