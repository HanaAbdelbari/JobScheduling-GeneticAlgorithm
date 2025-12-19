package NeuralNetworkLibrary.Model;

import NeuralNetworkLibrary.Layers.Layer;
import NeuralNetworkLibrary.Loss.LossFunction;
import NeuralNetworkLibrary.Utility.Dataset;
import NeuralNetworkLibrary.Utility.Matrix;

import java.util.ArrayList;
import java.util.Random;

/**
 * Central controller class that orchestrates the neural network framework.
 * Uses Matrix internally for all computations.
 */
public class NeuralNetwork {

    private ArrayList<Layer> layers;
    private LossFunction lossFunction;
    private double learningRate;
    private TrainingHistory trainingHistory;
    private Random random;

    public NeuralNetwork() {
        this(System.currentTimeMillis());
    }

    public NeuralNetwork(long seed) {
        this.layers = new ArrayList<>();
        this.trainingHistory = new TrainingHistory();
        this.learningRate = 0.01;
        this.random = new Random(seed);
    }

    /* ================= LAYER MANAGEMENT ================= */

    public void addLayer(Layer layer) {
        if (!layers.isEmpty()) {
            Layer prev = layers.get(layers.size() - 1);
            if (prev.getNeurons() != layer.getInputs()) {
                throw new IllegalArgumentException(
                        "Layer input mismatch: expected " +
                                prev.getNeurons() + " but got " +
                                layer.getInputs()
                );
            }
        }
        layers.add(layer);
    }

    public void setLoss(LossFunction loss) {
        this.lossFunction = loss;
    }

    public void setLearningRate(double learningRate) {
        if (learningRate <= 0) {
            throw new IllegalArgumentException("Learning rate must be positive.");
        }
        this.learningRate = learningRate;
    }

    /* ================= FORWARD ================= */

    private Matrix forward(Matrix X) {
        Matrix output = X;
        for (Layer layer : layers) {
            output = layer.forward(output);
        }
        return output;
    }

    /* ================= BACKWARD ================= */

    private void backward(Matrix yTrue, Matrix yPred) {
        Matrix dOut = lossFunction.derivative(yTrue, yPred);
        for (int i = layers.size() - 1; i >= 0; i--) {
            dOut = layers.get(i).backward(dOut, learningRate);
        }
    }

    /* ================= TRAINING ================= */

    public void train(Dataset dataset, int epochs, int batchSize) {

        if (layers.isEmpty()) {
            throw new IllegalStateException("No layers in network.");
        }
        if (lossFunction == null) {
            throw new IllegalStateException("Loss function not set.");
        }

        double[][] rawX = dataset.getX();
        double[][] rawY = dataset.getY();
        int n = dataset.size();

        System.out.println("\n=== Training Started ===");

        for (int epoch = 0; epoch < epochs; epoch++) {

            int[] indices = shuffleIndices(n);
            double epochLoss = 0.0;

            for (int start = 0; start < n; start += batchSize) {

                int end = Math.min(start + batchSize, n);
                int size = end - start;

                double[][] batchX = new double[size][];
                double[][] batchY = new double[size][];

                for (int i = 0; i < size; i++) {
                    batchX[i] = rawX[indices[start + i]];
                    batchY[i] = rawY[indices[start + i]];
                }

                Matrix X = new Matrix(batchX);
                Matrix Y = new Matrix(batchY);

                Matrix yPred = forward(X);
                double loss = lossFunction.loss(Y, yPred);

                epochLoss += loss * size;
                backward(Y, yPred);
            }

            epochLoss /= n;
            trainingHistory.addEpoch(epochLoss);

            if (epoch == 0 || (epoch + 1) % 10 == 0 || epoch == epochs - 1) {
                System.out.printf("Epoch %d/%d - Loss: %.6f%n",
                        epoch + 1, epochs, epochLoss);
            }
        }

        System.out.println("\n=== Training Complete ===");
        System.out.println(trainingHistory.getSummary());
    }

    /* ================= PREDICTION ================= */

    public double[][] predict(double[][] X) {
        Matrix result = forward(new Matrix(X));
        return result.getData();
    }

    public double[] predictSingle(double[] x) {
        double[][] input = new double[][]{x};
        return predict(input)[0];
    }

    /* ================= UTILITIES ================= */

    public TrainingHistory getTrainingHistory() {
        return trainingHistory;
    }

    public void summary() {
        System.out.println("\n=== Neural Network Summary ===");
        System.out.println("Layers: " + layers.size());
        System.out.println("Learning Rate: " + learningRate);
        System.out.println("Loss: " +
                (lossFunction != null ? lossFunction.getClass().getSimpleName() : "Not set"));

        int totalParams = 0;
        for (Layer layer : layers) {
            totalParams += layer.getInputs() * layer.getNeurons() + layer.getNeurons();
        }
        System.out.println("Total Parameters: " + totalParams);
        System.out.println("================================\n");
    }

    private int[] shuffleIndices(int n) {
        int[] idx = new int[n];
        for (int i = 0; i < n; i++) idx[i] = i;

        for (int i = n - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int tmp = idx[i];
            idx[i] = idx[j];
            idx[j] = tmp;
        }
        return idx;
    }
}
