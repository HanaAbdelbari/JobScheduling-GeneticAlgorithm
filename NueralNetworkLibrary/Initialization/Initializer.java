package NueralNetworkLibrary.Initialization;

 //set initial weights before training begins
public interface Initializer {
    double[][] initialize(int nInputs, int nNeurons);
}