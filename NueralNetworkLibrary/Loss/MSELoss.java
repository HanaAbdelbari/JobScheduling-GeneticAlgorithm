package NueralNetworkLibrary.Loss;

//used in Regression
public class MSELoss implements LossFunction{

    @Override
    public double loss(double[][] yTrue, double[][] yPred) {
        // From lecture: MSE = (1/2) * Σ(y_true - y_pred)²
        double sum = 0.0;
        int count = 0;
        for (int i = 0; i < yTrue.length; i++) {
            for (int j = 0; j < yTrue[0].length; j++) {
                double diff = yTrue[i][j] - yPred[i][j];
                sum += diff * diff;
                count++;
            }
        }
        return sum / count;
    }

    //used to update weights
    @Override
    public double[][] derivative(double[][] yTrue, double[][] yPred) {
        double[][] result = new double[yTrue.length][yTrue[0].length];
        int n = yTrue.length;
        for (int i = 0; i < yTrue.length; i++) {
            for (int j = 0; j < yTrue[0].length; j++) {
                result[i][j] = 2.0 * (yPred[i][j] - yTrue[i][j]) / n;
            }
        }
        return result;
    }

}
