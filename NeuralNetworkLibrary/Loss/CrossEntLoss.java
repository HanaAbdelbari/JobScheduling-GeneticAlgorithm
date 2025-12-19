package NeuralNetworkLibrary.Loss;

import NeuralNetworkLibrary.Utility.Matrix;

public class CrossEntLoss implements LossFunction {

    private static final double EPS = 1e-9;

    @Override
    public double loss(Matrix yTrue, Matrix yPred) {
        double sum = 0.0;
        int rows = yTrue.getRows();

        for (int i = 0; i < rows; i++) {
            double y = yTrue.getData()[i][0];
            double p = clip(yPred.getData()[i][0]);

            sum += y * Math.log(p) + (1 - y) * Math.log(1 - p);
        }
        return -sum / rows;
    }

    @Override
    public Matrix derivative(Matrix yTrue, Matrix yPred) {
        int rows = yTrue.getRows();
        Matrix grad = new Matrix(rows, 1);

        for (int i = 0; i < rows; i++) {
            double y = yTrue.getData()[i][0];
            double p = clip(yPred.getData()[i][0]);

            grad.getData()[i][0] = (p - y) / (p * (1 - p) + EPS);
        }
        return grad;
    }

    private double clip(double value) {
        return Math.max(EPS, Math.min(1 - EPS, value));
    }

    @Override
    public String name() {
        return "Binary Cross Entropy";
    }
}
