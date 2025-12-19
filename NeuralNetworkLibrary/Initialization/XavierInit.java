package NeuralNetworkLibrary.Initialization;

import java.util.Random;


 // Best for Sigmoid and Tanh activation functions

public class XavierInit implements Initializer {
    private Random random;

    public XavierInit() {
        this.random = new Random();
    }

    public XavierInit(long seed) {
        this.random = new Random(seed);
    }

    @Override
    public double[][] initialize(int nInputs, int nNeurons) {
        // Xavier formula
        double limit = Math.sqrt(6.0 / (nInputs + nNeurons));

        double[][] weights = new double[nInputs][nNeurons];
        for (int i = 0; i < nInputs; i++) {
            for (int j = 0; j < nNeurons; j++) {
                // Uniform distribution in [-limit, +limit]
                weights[i][j] = -limit + 2 * limit * random.nextDouble();
            }
        }
        return weights;
    }
}
