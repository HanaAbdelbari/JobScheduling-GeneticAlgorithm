package NueralNetworkLibrary.Initialization;

import java.util.Random;

public class RandomUniformInit implements Initializer {
    private double low;
    private double high;
    private Random random;

    public RandomUniformInit(double low, double high) {
        this.low = low;
        this.high = high;
        this.random = new Random();
    }

    public RandomUniformInit() {
        this(-0.5, 0.5);
    }

    public RandomUniformInit(double low, double high, long seed) {
        this.low = low;
        this.high = high;
        this.random = new Random(seed);
    }

    @Override
    public double[][] initialize(int nInputs, int nNeurons) {
        double[][] weights = new double[nInputs][nNeurons];
        for (int i = 0; i < nInputs; i++) {
            for (int j = 0; j < nNeurons; j++) {
                // Generate random value in [low, high]
                weights[i][j] = low + (high - low) * random.nextDouble();
            }
        }
        return weights;
    }
}