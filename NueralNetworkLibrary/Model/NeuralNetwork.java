package NeuralNetworkLibrary.Model;

import NeuralNetworkLibrary.Layers.Layer;
import NeuralNetworkLibrary.Loss.LossFunction;
import NeuralNetworkLibrary.Utility.Dataset;
import java.util.ArrayList;
import java.util.Random;

/**
 * Central controller class that orchestrates the neural network framework.
 * Manages layers, coordinates training, and provides prediction API.
 */
public class NeuralNetwork {
    private ArrayList<Layer> layers;
    private LossFunction lossFunction;
    private double learningRate;
    private TrainingHistory trainingHistory;
    private Random random;

    /**
     * Constructor initializes empty network with random seed.
     */
    public NeuralNetwork() {
        this(System.currentTimeMillis());
    }

    /**
     * Constructor initializes empty network with specified seed for reproducibility.
     * @param seed Random seed for data shuffling
     */
    public NeuralNetwork(long seed) {
        this.layers = new ArrayList<>();
        this.trainingHistory = new TrainingHistory();
        this.random = new Random(seed);
        this.learningRate = 0.01; // Default learning rate
    }

    /**
     * Add a layer to the network.
     * Validates layer compatibility with the previous layer.
     * @param layer Layer to add
     */
    public void addLayer(Layer layer) {
        if (!layers.isEmpty()) {
            Layer previousLayer = layers.get(layers.size() - 1);
            if (previousLayer.getNeurons() != layer.getInputs()) {
                throw new IllegalArgumentException(
                    "Layer input mismatch. Previous layer has " + 
                    previousLayer.getNeurons() + " neurons, but new layer expects " + 
                    layer.getInputs() + " inputs."
                );
            }
        }
        layers.add(layer);
    }

    /**
     * Set the loss function for the network.
     * @param loss Loss function to use
     */
    public void setLoss(LossFunction loss) {
        this.lossFunction = loss;
    }

    /**
     * Set the learning rate for training.
     * @param learningRate Learning rate (typically 0.001 to 0.1)
     */
    public void setLearningRate(double learningRate) {
        if (learningRate <= 0) {
            throw new IllegalArgumentException("Learning rate must be positive.");
        }
        this.learningRate = learningRate;
    }

    /**
     * Forward propagation through all layers.
     * @param X Input data [samples x features]
     * @return Output predictions [samples x outputs]
     */
    public double[][] forward(double[][] X) {
        if (layers.isEmpty()) {
            throw new IllegalStateException("Cannot forward propagate: no layers in network.");
        }

        double[][] output = X;
        for (Layer layer : layers) {
            output = layer.forward(output);
        }
        return output;
    }

    /**
     * Backward propagation through all layers.
     * Note: yPred is passed explicitly for simplified API design.
     * In a more advanced architecture, yPred could be cached from forward().
     * @param yTrue True labels [samples x outputs]
     * @param yPred Predicted labels [samples x outputs]
     */
    public void backward(double[][] yTrue, double[][] yPred) {
        if (lossFunction == null) {
            throw new IllegalStateException("Cannot backward propagate: loss function not set.");
        }

        // Compute loss gradient
        double[][] dOut = lossFunction.derivative(yTrue, yPred);

        // Backpropagate through layers in reverse order
        for (int i = layers.size() - 1; i >= 0; i--) {
            dOut = layers.get(i).backward(dOut, learningRate);
        }
    }

    /**
     * Train the network on a dataset.
     * The loss recorded in history is the average epoch loss across all samples.
     * @param dataset Training dataset
     * @param epochs Number of training epochs
     * @param batchSize Size of mini-batches
     */
    public void train(Dataset dataset, int epochs, int batchSize) {
        // === Centralized Validation ===
        if (layers.isEmpty()) {
            throw new IllegalStateException("Cannot train: no layers in network.");
        }
        if (lossFunction == null) {
            throw new IllegalStateException("Cannot train: loss function not set.");
        }
        if (epochs <= 0) {
            throw new IllegalArgumentException("Epochs must be positive. Got: " + epochs);
        }
        if (batchSize <= 0) {
            throw new IllegalArgumentException("Batch size must be positive. Got: " + batchSize);
        }
        if (dataset.size() <= 0) {
            throw new IllegalArgumentException("Dataset is empty.");
        }
        if (dataset.getFeatureCount() != layers.get(0).getInputs()) {
            throw new IllegalArgumentException(
                "Dataset feature count (" + dataset.getFeatureCount() + 
                ") does not match input size of first layer (" + 
                layers.get(0).getInputs() + ")."
            );
        }

        double[][] X = dataset.getX();
        double[][] Y = dataset.getY();
        int numSamples = dataset.size();

        System.out.println("\n=== Training Started ===");
        System.out.println("Epochs: " + epochs);
        System.out.println("Batch Size: " + batchSize);
        System.out.println("Learning Rate: " + learningRate);
        System.out.println("Samples: " + numSamples);
        System.out.println("========================\n");

        for (int epoch = 0; epoch < epochs; epoch++) {
            // Shuffle data
            int[] indices = shuffleIndices(numSamples);

            double epochLoss = 0.0;
            int numBatches = (int) Math.ceil((double) numSamples / batchSize);

            // Iterate over batches
            for (int batchIdx = 0; batchIdx < numBatches; batchIdx++) {
                int startIdx = batchIdx * batchSize;
                int endIdx = Math.min(startIdx + batchSize, numSamples);
                int currentBatchSize = endIdx - startIdx;

                // Extract batch
                double[][] batchX = new double[currentBatchSize][];
                double[][] batchY = new double[currentBatchSize][];

                for (int i = 0; i < currentBatchSize; i++) {
                    batchX[i] = X[indices[startIdx + i]];
                    batchY[i] = Y[indices[startIdx + i]];
                }

                // Forward pass
                double[][] yPred = forward(batchX);

                // Compute loss
                double batchLoss = lossFunction.loss(batchY, yPred);
                epochLoss += batchLoss * currentBatchSize;

                // Backward pass
                backward(batchY, yPred);
            }

            // Average loss over all samples (epoch loss)
            epochLoss /= numSamples;
            
            // TODO: Add accuracy computation for classification tasks
            // double accuracy = computeAccuracy(Y, forward(X));
            // trainingHistory.addEpoch(epochLoss, accuracy);
            
            trainingHistory.addEpoch(epochLoss);

            // Print progress every 10 epochs or on the last epoch
            if ((epoch + 1) % 10 == 0 || epoch == 0 || epoch == epochs - 1) {
                System.out.printf("Epoch %d/%d - Loss: %.6f\n", 
                    epoch + 1, epochs, epochLoss);
            }
        }

        System.out.println("\n=== Training Complete ===\n");
        System.out.println(trainingHistory.getSummary());
    }

    /**
     * Make predictions on new data.
     * @param X Input data [samples x features]
     * @return Predictions [samples x outputs]
     */
    public double[][] predict(double[][] X) {
        return forward(X);
    }

    /**
     * Make a prediction on a single sample.
     * @param x Single input sample [features]
     * @return Prediction [outputs]
     */
    public double[] predictSingle(double[] x) {
        double[][] X = new double[1][];
        X[0] = x;
        double[][] predictions = predict(X);
        return predictions[0];
    }

    /**
     * Get the training history.
     * @return TrainingHistory object
     */
    public TrainingHistory getTrainingHistory() {
        return trainingHistory;
    }

    /**
     * Compute classification accuracy (percentage of correct predictions).
     * TODO: Currently not integrated into training loop.
     * This will be activated when classification tasks are implemented.
     * @param yTrue True labels [samples x outputs]
     * @param yPred Predicted labels [samples x outputs]
     * @return Accuracy as a fraction (0.0 to 1.0)
     */
    private double computeAccuracy(double[][] yTrue, double[][] yPred) {
        int correct = 0;
        int total = yTrue.length;

        for (int i = 0; i < total; i++) {
            // For binary classification: round to 0 or 1
            // For multi-class: argmax of yPred vs yTrue
            int predictedClass = yPred[i][0] >= 0.5 ? 1 : 0;
            int trueClass = (int) Math.round(yTrue[i][0]);
            
            if (predictedClass == trueClass) {
                correct++;
            }
        }

        return (double) correct / total;
    }

    /**
     * Print network architecture summary.
     */
    public void summary() {
        System.out.println("\n=== Neural Network Architecture ===");
        System.out.println("Total Layers: " + layers.size());
        System.out.println("Learning Rate: " + learningRate);
        System.out.println("Loss Function: " + 
            (lossFunction != null ? lossFunction.getClass().getSimpleName() : "Not set"));
        System.out.println("\nLayer Details:");
        System.out.println("Layer\tInputs\tNeurons\tParameters");
        System.out.println("-----\t------\t-------\t----------");

        int totalParams = 0;
        for (int i = 0; i < layers.size(); i++) {
            Layer layer = layers.get(i);
            int inputs = layer.getInputs();
            int neurons = layer.getNeurons();
            int params = (inputs * neurons) + neurons; // weights + biases
            totalParams += params;

            System.out.printf("%d\t%d\t%d\t%d\n", i + 1, inputs, neurons, params);
        }

        System.out.println("\nTotal Parameters: " + totalParams);
        System.out.println("===================================\n");
    }

    /**
     * Get the number of layers in the network.
     * @return Number of layers
     */
    public int getLayerCount() {
        return layers.size();
    }

    /**
     * Shuffle array indices for data randomization.
     * @param n Size of the array
     * @return Shuffled indices
     */
    private int[] shuffleIndices(int n) {
        int[] indices = new int[n];
        for (int i = 0; i < n; i++) {
            indices[i] = i;
        }

        // Fisher-Yates shuffle
        for (int i = n - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = indices[i];
            indices[i] = indices[j];
            indices[j] = temp;
        }

        return indices;
    }
}
