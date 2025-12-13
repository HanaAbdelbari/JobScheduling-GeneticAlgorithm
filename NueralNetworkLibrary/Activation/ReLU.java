package NueralNetworkLibrary.Activation;

//Most common hidden layer activation,Prevents vanishing gradient
public class ReLU implements ActivationFunction {
  //Zeroes negative values , Keeps positive values unchanged
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

    //Correct ReLU gradient
    //Stops gradient for inactive neurons
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
