package NeuralNetworkLibrary.Activation;

import NeuralNetworkLibrary.Utility.Matrix;

public class Sigmoid implements ActivationFunction {

    private static final double CLIP = 500;

    @Override
    public Matrix activate(Matrix z) {
        return z.apply(v -> {
            double clipped = Math.max(-CLIP, Math.min(CLIP, v));
            return 1.0 / (1.0 + Math.exp(-clipped));
        });
    }

    @Override
    public Matrix derivative(Matrix z) {
        Matrix sigmoid = activate(z);
        return sigmoid.apply(v -> v * (1.0 - v));
    }

    @Override
    public String name() {
        return "Sigmoid";
    }
}
