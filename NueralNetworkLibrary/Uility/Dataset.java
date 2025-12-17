package NueralNetworkLibrary.Uility;

import NueralNetworkLibrary.ExceptionHandlers.ShapeMismatchException;

public class Dataset {

    private final Matrix features; // X
    private final Matrix labels;   // y

    public Dataset(Matrix features, Matrix labels) {

        if (features.getRows() != labels.getRows()) {
            throw new ShapeMismatchException(
                    "Number of samples in features and labels must match."
            );
        }
        this.features = features;
        this.labels = labels;
    }

    public Matrix getFeatures() {
        return features;
    }

    public Matrix getLabels() {
        return labels;
    }

    public int size() {
        return features.getRows();
    }


    // Train/Test split
    public Dataset[] trainTestSplit(double trainRatio) {
        if (trainRatio <= 0 || trainRatio >= 1) {
            throw new IllegalArgumentException("Train ratio must be between 0 and 1.");
        }

        int total = size();
        int trainSize = (int) (total * trainRatio);

        Matrix X_train = new Matrix(trainSize, features.getCols());
        Matrix y_train = new Matrix(trainSize, labels.getCols());

        Matrix X_test = new Matrix(total - trainSize, features.getCols());
        Matrix y_test = new Matrix(total - trainSize, labels.getCols());

        for (int i = 0; i < total; i++) {
            if (i < trainSize) {
                X_train.getData()[i] = features.getData()[i];
                y_train.getData()[i] = labels.getData()[i];
            } else {
                X_test.getData()[i - trainSize] = features.getData()[i];
                y_test.getData()[i - trainSize] = labels.getData()[i];
            }
        }

        return new Dataset[]{
                new Dataset(X_train, y_train),
                new Dataset(X_test, y_test)
        };
    }

// shuffle
public void shuffle() {
    int n = size();

    for (int i = n - 1; i > 0; i--) {
        int j = (int) (Math.random() * (i + 1));

        double[] tempX = features.getData()[i];
        features.getData()[i] = features.getData()[j];
        features.getData()[j] = tempX;

        double[] tempY = labels.getData()[i];
        labels.getData()[i] = labels.getData()[j];
        labels.getData()[j] = tempY;
    }
}
}