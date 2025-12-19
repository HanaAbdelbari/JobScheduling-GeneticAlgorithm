package CaseStudies.SpamDetectionCaseStudy;

import NeuralNetworkLibrary.Activation.*;
import NeuralNetworkLibrary.ExceptionHandlers.InvalidHyperParameterException;
import NeuralNetworkLibrary.ExceptionHandlers.LayerConfigurationException;
import NeuralNetworkLibrary.Initialization.XavierInit;
import NeuralNetworkLibrary.Layers.DenseLayer;
import NeuralNetworkLibrary.Loss.*;
import NeuralNetworkLibrary.Model.NeuralNetwork;
import NeuralNetworkLibrary.Utility.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConfigurableSpamDemo {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Spam Detection Neural Network ===\n");

        /* =====================
           1. Architecture
           ===================== */
        System.out.print("Hidden layers (1-5): ");
        int hiddenLayers = getInt(scanner, 1, 5);

        List<Integer> layerSizes = new ArrayList<>();
        for (int i = 0; i < hiddenLayers; i++) {
            System.out.printf("Neurons in layer %d (1-256): ", i + 1);
            layerSizes.add(getInt(scanner, 1, 256));
        }

        /* =====================
           2. Activation
           ===================== */
        System.out.println("\nActivation:");
        System.out.println("1. ReLU");
        System.out.println("2. Sigmoid");
        System.out.println("3. Tanh");
        System.out.println("4. Linear");

        ActivationFunction hiddenActivation =
                getActivation(getInt(scanner, 1, 4));
        ActivationFunction outputActivation = new Sigmoid();

        /* =====================
           3. Loss
           ===================== */
        System.out.println("\nLoss:");
        System.out.println("1. Binary Cross Entropy");
        System.out.println("2. Mean Squared Error");

        LossFunction loss =
                getInt(scanner, 1, 2) == 1
                        ? new CrossEntLoss()
                        : new MSELoss();

        /* =====================
           4. Training Params
           ===================== */
        System.out.print("\nLearning rate: ");
        double lr = getDouble(scanner, 0.0001, 1.0);
        Validator.validateLearningRate(lr);

        System.out.print("Epochs (10-5000): ");
        int epochs = getInt(scanner, 10, 5000);
        Validator.validateEpochs(epochs);

        System.out.print("Batch size (1-256): ");
        int batchSize = getInt(scanner, 1, 256);

        /* =====================
           5. Load Dataset
           ===================== */
        SpamRawDataset raw = SpamDatasetLoader.load("data/spam.csv");

        TextVectoraizer vectorizer = new TextVectoraizer(1000);
        Matrix X = vectorizer.fitTransform(raw.texts);
        Matrix Y = new Matrix(raw.labels);

        Dataset full = new Dataset(X, Y);
        Validator.validateDataset(full);

        Dataset[] split = TrainTestSplit.split(full, 0.2, true, 42);
        Dataset trainSet = split[0];
        Dataset testSet  = split[1];

        Validator.validateBatchSize(batchSize, trainSet.size());

        /* =====================
           6. Build Model
           ===================== */
        NeuralNetwork nn = new NeuralNetwork(42);

        int inputSize = X.getCols();
        for (int neurons : layerSizes) {
            validateLayer(inputSize, neurons, hiddenActivation);

            nn.addLayer(new DenseLayer(
                    inputSize,
                    neurons,
                    hiddenActivation,
                    new XavierInit()
            ));
            inputSize = neurons;
        }

        nn.addLayer(new DenseLayer(
                inputSize,
                1,
                outputActivation,
                new XavierInit()
        ));

        nn.setLoss(loss);
        nn.setLearningRate(lr);

        nn.summary();

        /* =====================
           7. Train
           ===================== */
        nn.train(trainSet, epochs, batchSize);

        /* =====================
           8. Evaluate
           ===================== */
        Matrix preds = nn.predict(testSet.getX());
        Matrix yTrue = testSet.getY();

        int correct = 0;
        int n = testSet.size();

        for (int i = 0; i < n; i++) {
            int p = preds.getData()[i][0] >= 0.5 ? 1 : 0;
            int y = (int) yTrue.getData()[i][0];
            if (p == y) correct++;
        }

        System.out.printf(
                "\nTest Accuracy: %.2f%%%n",
                (correct / (double) n) * 100
        );

        scanner.close();
    }

    /* =====================
       Helpers
       ===================== */

    private static void validateLayer(int in, int out, ActivationFunction act) {
        if (in <= 0 || out <= 0 || act == null)
            throw new LayerConfigurationException("Invalid layer configuration.");
    }

    private static int getInt(Scanner sc, int min, int max) {
        while (true) {
            try {
                int v = Integer.parseInt(sc.nextLine().trim());
                if (v < min || v > max) throw new InvalidHyperParameterException("");
                return v;
            } catch (Exception e) {
                System.out.printf("Enter [%d-%d]: ", min, max);
            }
        }
    }

    private static double getDouble(Scanner sc, double min, double max) {
        while (true) {
            try {
                double v = Double.parseDouble(sc.nextLine().trim());
                if (v < min || v > max) throw new InvalidHyperParameterException("");
                return v;
            } catch (Exception e) {
                System.out.printf("Enter [%.4f-%.4f]: ", min, max);
            }
        }
    }

    private static ActivationFunction getActivation(int c) {
        return switch (c) {
            case 1 -> new ReLU();
            case 2 -> new Sigmoid();
            case 3 -> new Tanh();
            case 4 -> new Linear();
            default -> throw new InvalidHyperParameterException("Invalid activation");
        };
    }
}
