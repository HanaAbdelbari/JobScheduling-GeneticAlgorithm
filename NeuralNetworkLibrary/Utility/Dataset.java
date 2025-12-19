package NeuralNetworkLibrary.Utility;

/**
 * Dataset container for training and testing data.
 * Holds feature matrix X and label matrix Y.
 */
public class Dataset {

    private final Matrix X;  // [samples x features]
    private final Matrix Y;  // [samples x outputs]

    public Dataset(Matrix X, Matrix Y) {
        if (X == null || Y == null) {
            throw new IllegalArgumentException("X and Y cannot be null.");
        }
        if (X.getRows() != Y.getRows()) {
            throw new IllegalArgumentException(
                    "Number of samples in X and Y must match. Got X: " +
                            X.getRows() + ", Y: " + Y.getRows()
            );
        }
        this.X = X;
        this.Y = Y;
    }

    public Matrix getX() {
        return X;
    }

    public Matrix getY() {
        return Y;
    }

    public int size() {
        return X.getRows();
    }

    public int getFeatureCount() {
        return X.getCols();
    }

    public int getOutputCount() {
        return Y.getCols();
    }

    /**
     * Create a subset of the dataset.
     */
    public Dataset subset(int startIdx, int endIdx) {
        if (startIdx < 0 || endIdx > size() || startIdx >= endIdx) {
            throw new IllegalArgumentException(
                    "Invalid subset range: [" + startIdx + ", " + endIdx + ")"
            );
        }

        double[][] subX = new double[endIdx - startIdx][X.getCols()];
        double[][] subY = new double[endIdx - startIdx][Y.getCols()];

        for (int i = startIdx; i < endIdx; i++) {
            subX[i - startIdx] = X.getData()[i].clone();
            subY[i - startIdx] = Y.getData()[i].clone();
        }

        return new Dataset(new Matrix(subX), new Matrix(subY));
    }

    public void printInfo() {
        System.out.println("Dataset Info:");
        System.out.println("  Samples: " + size());
        System.out.println("  Features: " + getFeatureCount());
        System.out.println("  Outputs: " + getOutputCount());
    }
}