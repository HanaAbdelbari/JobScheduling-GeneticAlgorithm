package NeuralNetworkLibrary.Activation;

public class Sigmoid implements ActivationFunction{

    @Override
    public double[][] activate(double[][] z) {
        double[][] result = new double[z.length][z[0].length];
        for (int i = 0; i < z.length; i++) {
            for (int j = 0; j < z[0].length; j++) {
                // Clip to prevent overflow
                double clipped = Math.max(-500, Math.min(500, z[i][j]));
                result[i][j] = 1.0 / (1.0 + Math.exp(-clipped));
            }
        }
        return result;
    }

    @Override
    public double[][] derivative(double[][] z) {
        // Lecture formula: sigmoid(z) * (1 - sigmoid(z))
        double[][] activated = activate(z);
        double[][] result = new double[z.length][z[0].length];
        for (int i = 0; i < z.length; i++) {
            for (int j = 0; j < z[0].length; j++) {
                result[i][j] = activated[i][j] * (1.0 - activated[i][j]);
            }
        }
        return result;
    }

    @Override
    public String name() {
        return "Sigmoid";
    }

    }


