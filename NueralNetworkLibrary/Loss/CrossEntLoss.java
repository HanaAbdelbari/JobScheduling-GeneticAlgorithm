package NeuralNetworkLibrary.Loss;

//Binary Cross Entropy used in classification

public class CrossEntLoss implements LossFunction{

    //to protect from log(0),division by zero
    private static final double EPSILON = 1e-15;

    @Override
    public double loss(double[][] yTrue, double[][] yPred) {
        double sum = 0.0;
        int count = 0;
        for (int i = 0; i < yTrue.length; i++) {
            for (int j = 0; j < yTrue[0].length; j++) {

                // Clip to prevent log(0) and make values between(ε , 1−ε)
                double pred = Math.max(EPSILON, Math.min(1 - EPSILON, yPred[i][j]));

                //if predection is true , loss is small and vice versa
                sum += -(yTrue[i][j] * Math.log(pred) +
                        (1 - yTrue[i][j]) * Math.log(1 - pred));
                count++;
            }
        }
        return sum / count;
    }

    @Override
    public double[][] derivative(double[][] yTrue, double[][] yPred) {

        //it is the derivative of Cross Entropy
        double[][] result = new double[yTrue.length][yTrue[0].length];
        int n = yTrue.length;
        for (int i = 0; i < yTrue.length; i++) {
            for (int j = 0; j < yTrue[0].length; j++) {
                double pred = Math.max(EPSILON, Math.min(1 - EPSILON, yPred[i][j]));
                result[i][j] = (pred - yTrue[i][j]) / (pred * (1 - pred)) / n;
            }
        }
        return result;
    }

}
