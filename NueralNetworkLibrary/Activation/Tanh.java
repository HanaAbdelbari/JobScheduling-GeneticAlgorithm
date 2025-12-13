package NueralNetworkLibrary.Activation;

public class Tanh implements ActivationFunction{

    @Override
    public double[][] activate(double[][] z) {
        double[][] result = new double[z.length][z[0].length];
        for (int i = 0; i < z.length; i++) {
            for (int j = 0; j < z[0].length; j++) {
                result[i][j] = Math.tanh(z[i][j]);
            }
        }
        return result;
    }

    @Override
    public double[][] derivative(double[][] z) {
        double[][] result = new double[z.length][z[0].length];
        for (int i = 0; i < z.length; i++) {
            for (int j = 0; j < z[0].length; j++) {
                double tanh = Math.tanh(z[i][j]);
                result[i][j] = 1.0 - tanh * tanh;
            }
        }
        return result;
    }

    @Override
    public String name() {
        return "Tanh";
    }

}
