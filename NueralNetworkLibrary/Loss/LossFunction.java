package NeuralNetworkLibrary.Loss;

public interface LossFunction {

    double loss(double[][] yTrue, double[][] yPred);

    //returns Gradient
    double[][] derivative(double[][] yTrue, double[][] yPred);

}
