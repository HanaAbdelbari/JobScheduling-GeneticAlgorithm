package NueralNetworkLibrary.Activation;

public class Linear implements ActivationFunction{

    @Override
    public double[][] activate(double[][] z) {
        return z; // Return unchanged
    }

    //used in the output layer for regression problems

    @Override
    public double[][] derivative(double[][] z) {
        double[][] result = new double[z.length][z[0].length];
        for (int i = 0; i < z.length; i++) {
            for (int j = 0; j < z[0].length; j++) {
                result[i][j] = 1.0;
            }
        }
        return result;
    }

    @Override
    public String name() {
        return "Linear";
    }

}
