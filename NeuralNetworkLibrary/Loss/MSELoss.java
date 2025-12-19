package NeuralNetworkLibrary.Loss;

import NeuralNetworkLibrary.Utility.Matrix;

public class MSELoss implements LossFunction {

    @Override
    public double loss(Matrix yTrue, Matrix yPred) {
        double sum = 0.0;
        int rows = yTrue.getRows();
        int cols = yTrue.getCols();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double diff = yTrue.getData()[i][j] - yPred.getData()[i][j];
                sum += diff * diff;
            }
        }
        return sum / rows;
    }

    @Override
    public Matrix derivative(Matrix yTrue, Matrix yPred) {
        int rows = yTrue.getRows();
        int cols = yTrue.getCols();
        Matrix grad = new Matrix(rows, cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grad.getData()[i][j] =
                        2.0 * (yPred.getData()[i][j] - yTrue.getData()[i][j]) / rows;
            }
        }
        return grad;
    }

    @Override
    public String name() {
        return "Mean Squared Error";
    }
}
