package NueralNetworkLibrary.Activation;

public class ReLU implements ActivationFunction {

    @Override
    public double[][] activate(double[][] z) {
        double[][] result = new double[z.length][z[0].length];
        for (int i = 0; i < z.length; i++) {
            for (int j = 0; j < z[0].length; j++) {
                result[i][j] = Math.max(0, z[i][j]);
            }
        }
        return result;
    }

    @Override
    public double[][] derivative(double[][] z) {
        double[][] result = new double[z.length][z[0].length];
        for (int i = 0; i < z.length; i++) {
            for (int j = 0; j < z[0].length; j++) {
                result[i][j] = z[i][j] > 0 ? 1.0 : 0.0;
            }
        }
        return result;
    }

    @Override
    public String name() {
        return "ReLU";
    }

}
