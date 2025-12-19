package CaseStudies.SpamDetectionCaseStudy;

import NeuralNetworkLibrary.Activation.ReLU;
import NeuralNetworkLibrary.Activation.Sigmoid;
import NeuralNetworkLibrary.Initialization.XavierInit;
import NeuralNetworkLibrary.Layers.DenseLayer;
import NeuralNetworkLibrary.Loss.CrossEntLoss;
import NeuralNetworkLibrary.Model.NeuralNetwork;
import NeuralNetworkLibrary.Utility.Dataset;
import NeuralNetworkLibrary.Utility.Matrix;

public class SpamDemo {

    public static void main(String[] args) {

        // =====================
        // 1. Load Raw Dataset
        // =====================
        String datasetPath = "data/spam.csv"; // adjust path if needed
        SpamRawDataset raw = SpamDatasetLoader.load(datasetPath);

        // =====================
        // 2. Vectorization
        // =====================
        TextVectoraizer vectorizer = new TextVectoraizer(1000);
        Matrix X = vectorizer.fitTransform(raw.texts);
        Matrix Y = new Matrix(raw.labels);

        Dataset dataset = new Dataset(X.getData(), Y.getData());

        // =====================
        // 3. Model
        // =====================
        NeuralNetwork nn = new NeuralNetwork(42);

        nn.addLayer(new DenseLayer(
                X.getCols(), 16,
                new ReLU(),
                new XavierInit()
        ));

        nn.addLayer(new DenseLayer(
                16, 1,
                new Sigmoid(),
                new XavierInit()
        ));

        nn.setLoss(new CrossEntLoss());
        nn.setLearningRate(0.05);

        nn.summary();

        // =====================
        // 4. Training
        // =====================
        nn.train(dataset, 500, 32);

        // =====================
        // 5. Evaluation
        // =====================
        double[][] predictions = nn.predict(X.getData());

        System.out.println("\nPredictions:");
        for (int i = 0; i < raw.texts.length; i++) {
            int pred = predictions[i][0] > 0.5 ? 1 : 0;
            int truth = (int) raw.labels[i][0];

            System.out.printf(
                    "Email: %-40s | Pred: %d | True: %d%n",
                    raw.texts[i], pred, truth
            );
        }
    }
}
