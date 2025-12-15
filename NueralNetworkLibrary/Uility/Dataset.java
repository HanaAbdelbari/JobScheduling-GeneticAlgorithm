package NeuralNetworkLibrary.Utility;

/**
 * Dataset container for training and testing data.
 * Holds feature matrix X and label matrix Y.
 */
public class Dataset {
    private double[][] X;  // Features [samples x features]
    private double[][] Y;  // Labels [samples x outputs]

    /**
     * Constructor for dataset.
     * @param X Feature matrix [samples x features]
     * @param Y Label matrix [samples x outputs]
     */
    public Dataset(double[][] X, double[][] Y) {
        if (X.length != Y.length) {
            throw new IllegalArgumentException(
                "Number of samples in X and Y must match. Got X: " + 
                X.length + ", Y: " + Y.length
            );
        }
        this.X = X;
        this.Y = Y;
    }

    /**
     * Get the feature matrix.
     * @return Feature matrix X
     */
    public double[][] getX() {
        return X;
    }

    /**
     * Get the label matrix.
     * @return Label matrix Y
     */
    public double[][] getY() {
        return Y;
    }

    /**
     * Get the number of samples in the dataset.
     * @return Number of samples
     */
    public int size() {
        return X.length;
    }

    /**
     * Get the number of features per sample.
     * @return Number of features
     */
    public int getFeatureCount() {
        return X[0].length;
    }

    /**
     * Get the number of output labels per sample.
     * @return Number of outputs
     */
    public int getOutputCount() {
        return Y[0].length;
    }

    /**
     * Get a subset of the dataset.
     * @param startIdx Start index (inclusive)
     * @param endIdx End index (exclusive)
     * @return New Dataset containing the specified range
     */
    public Dataset subset(int startIdx, int endIdx) {
        if (startIdx < 0 || endIdx > X.length || startIdx >= endIdx) {
            throw new IllegalArgumentException(
                "Invalid subset range: [" + startIdx + ", " + endIdx + ")"
            );
        }

        int subsetSize = endIdx - startIdx;
        double[][] subX = new double[subsetSize][];
        double[][] subY = new double[subsetSize][];

        for (int i = 0; i < subsetSize; i++) {
            subX[i] = X[startIdx + i];
            subY[i] = Y[startIdx + i];
        }

        return new Dataset(subX, subY);
    }

    /**
     * Print dataset information.
     */
    public void printInfo() {
        System.out.println("Dataset Info:");
        System.out.println("  Samples: " + size());
        System.out.println("  Features: " + getFeatureCount());
        System.out.println("  Outputs: " + getOutputCount());
    }
}
