package NueralNetworkLibrary.Layers;

import NueralNetworkLibrary.Activation.ActivationFunction;
import NueralNetworkLibrary.Initialization.Initializer;


public class DenseLayer extends Layer {


    public DenseLayer(int nInputs, int nNeurons, ActivationFunction activation,
                      Initializer initializer) {
        super(nInputs, nNeurons, activation, initializer);
    }

    //JUST FOR: better debugging

    @Override
    public String toString() {
        return String.format("DenseLayer(inputs=%d, neurons=%d, activation=%s)",
                nInputs, nNeurons, activation.name());
    }
}
