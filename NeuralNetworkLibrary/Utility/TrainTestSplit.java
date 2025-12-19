package NeuralNetworkLibrary.Utility;

import java.util.Random;

/**
 * Utility class for splitting datasets into training and testing sets.
 */
public final class TrainTestSplit {

    // Prevent instantiation
    private TrainTestSplit() {}

    /**
     * Split dataset into train and test sets.
     *
     * @param dataset   Full dataset
     * @param testRatio Ratio of test set (e.g., 0.2 = 20%)
     * @param shuffle   Whether to shuffle before splitting
     * @param seed      Random seed for reproducibility
     * @return Dataset array: [train, test]
     */
    public static Dataset[] split(
            Dataset dataset,
            double testRatio,
            boolean shuffle,
            long seed
    ) {
        Validator.validateDataset(dataset);
        Validator.validateSplitRatio(testRatio);

        Matrix X = dataset.getX();
        Matrix Y = dataset.getY();

        int totalSamples = X.getRows();
        int testSize = (int) Math.round(totalSamples * testRatio);
        int trainSize = totalSamples - testSize;

        int[] indices = new int[totalSamples];
        for (int i = 0; i < totalSamples; i++) {
            indices[i] = i;
        }

        if (shuffle) {
            shuffleIndices(indices, seed);
        }

        // Allocate matrices
        Matrix XTrain = new Matrix(trainSize, X.getCols());
        Matrix YTrain = new Matrix(trainSize, Y.getCols());
        Matrix XTest  = new Matrix(testSize, X.getCols());
        Matrix YTest  = new Matrix(testSize, Y.getCols());

        // Fill train set
        for (int i = 0; i < trainSize; i++) {
            int idx = indices[i];
            copyRow(X, XTrain, idx, i);
            copyRow(Y, YTrain, idx, i);
        }

        // Fill test set
        for (int i = 0; i < testSize; i++) {
            int idx = indices[trainSize + i];
            copyRow(X, XTest, idx, i);
            copyRow(Y, YTest, idx, i);
        }

        return new Dataset[] {
                new Dataset(XTrain, YTrain),
                new Dataset(XTest, YTest)
        };
    }

    /* =====================
       Helper Methods
       ===================== */

    private static void shuffleIndices(int[] indices, long seed) {
        Random random = new Random(seed);
        for (int i = indices.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = indices[i];
            indices[i] = indices[j];
            indices[j] = temp;
        }
    }

    private static void copyRow(Matrix src, Matrix dst, int srcRow, int dstRow) {
        for (int j = 0; j < src.getCols(); j++) {
            dst.getData()[dstRow][j] = src.getData()[srcRow][j];
        }
    }
}
