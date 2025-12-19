package NeuralNetworkLibrary.Model;

import NeuralNetworkLibrary.Layers.Layer;
import NeuralNetworkLibrary.Loss.LossFunction;
import NeuralNetworkLibrary.Utility.Dataset;
import NeuralNetworkLibrary.Utility.Matrix;

import java.util.ArrayList;
import java.util.Random;

/**
 * Central controller class for the neural network.
 * Fully Matrix-based implementation.
 */
public class NeuralNetwork {

    private final ArrayList<Layer> layers = new ArrayList<>();
    private LossFunction lossFunction;
    private double learningRate = 0.01;

    private final TrainingHistory trainingHistory = new TrainingHistory();
    private final Random random;

    public NeuralNetwork() {
        this(System.currentTimeMillis());
    }

    public NeuralNetwork(long seed) {
        this.random = new Random(seed);
    }

    /* =====================
       CONFIGURATION
       ===================== */

    public void addLayer(Layer layer) {
        if (!layers.isEmpty()) {
            Layer prev = layers.get(layers.size() - 1);
            if (prev.getNeurons() != layer.getInputs()) {
                throw new IllegalArgumentException(
                        "Layer input mismatch: previous=" +
                                prev.getNeurons() + ", new=" + layer.getInputs()
                );
            }
        }
        layers.add(layer);
    }

    public void setLoss(LossFunction lossFunction) {
        this.lossFunction = lossFunction;
    }

    public void setLearningRate(double learningRate) {
        if (learningRate <= 0) {
            throw new IllegalArgumentException("Learning rate must be positive.");
        }
        this.learningRate = learningRate;
    }

    /* =====================
       FORWARD / BACKWARD
       ===================== */

    public Matrix forward(Matrix X) {
        Matrix output = X;
        for (Layer layer : layers) {
            output = layer.forward(output);
        }
        return output;
    }

    public void backward(Matrix yTrue, Matrix yPred) {
        Matrix dOut = lossFunction.derivative(yTrue, yPred);

        for (int i = layers.size() - 1; i >= 0; i--) {
            dOut = layers.get(i).backward(dOut, learningRate);
        }
    }

    /* =====================
       TRAINING
       ===================== */

    public void train(Dataset dataset, int epochs, int batchSize) {

        if (layers.isEmpty()) {
            throw new IllegalStateException("No layers in network.");
        }
        if (lossFunction == null) {
            throw new IllegalStateException("Loss function not set.");
        }

        Matrix X = dataset.getX();
        Matrix Y = dataset.getY();
        int samples = dataset.size();

        for (int epoch = 0; epoch < epochs; epoch++) {

            int[] indices = shuffleIndices(samples);
            double epochLoss = 0.0;

            for (int i = 0; i < samples; i += batchSize) {
                int end = Math.min(i + batchSize, samples);

                Matrix batchX = sliceRows(X, indices, i, end);
                Matrix batchY = sliceRows(Y, indices, i, end);

                Matrix yPred = forward(batchX);
                double loss = lossFunction.loss(batchY, yPred);
                epochLoss += loss * (end - i);

                backward(batchY, yPred);
            }

            epochLoss /= samples;
            trainingHistory.addEpoch(epochLoss);

            if (epoch == 0 || (epoch + 1) % 10 == 0 || epoch == epochs - 1) {
                System.out.printf("Epoch %d/%d - Loss: %.6f%n",
                        epoch + 1, epochs, epochLoss);
            }
        }
    }

    /* =====================
       PREDICTION
       ===================== */

    public Matrix predict(Matrix X) {
        return forward(X);
    }

    /* =====================
       UTILITIES
       ===================== */

    private int[] shuffleIndices(int n) {
        int[] indices = new int[n];
        for (int i = 0; i < n; i++) indices[i] = i;

        for (int i = n - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int tmp = indices[i];
            indices[i] = indices[j];
            indices[j] = tmp;
        }
        return indices;
    }

    private Matrix sliceRows(Matrix m, int[] indices, int start, int end) {
        double[][] data = new double[end - start][m.getCols()];
        for (int i = start; i < end; i++) {
            data[i - start] = m.getData()[indices[i]].clone();
        }
        return new Matrix(data);
    }

    public TrainingHistory getTrainingHistory() {
        return trainingHistory;
    }

    public void summary() {
        System.out.println("\n=== Neural Network Summary ===");
        System.out.println("Layers: " + layers.size());
        System.out.println("Learning Rate: " + learningRate);
        System.out.println("Loss: " +
                (lossFunction != null ? lossFunction.getClass().getSimpleName() : "None"));

        int params = 0;
        for (Layer l : layers) {
            params += l.getInputs() * l.getNeurons() + l.getNeurons();
        }
        System.out.println("Total Parameters: " + params);
        System.out.println("==============================\n");
    }
}
