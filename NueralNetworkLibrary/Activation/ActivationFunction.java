package NueralNetworkLibrary.Activation;

public interface ActivationFunction {
    //forward propagation
    double[][] activate(double[][] z);
    //used during backpropagation
    double[][] derivative(double[][] z);

    String name();
}


