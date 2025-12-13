package NueralNetworkLibrary.Activation;

public interface ActivationFunction {

    double[][] activate(double[][] z);
    double[][] derivative(double[][] z);
    String name();
}


