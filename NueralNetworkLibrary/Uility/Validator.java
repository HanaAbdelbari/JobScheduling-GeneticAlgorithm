package NueralNetworkLibrary.Uility;

import NueralNetworkLibrary.ExceptionHandlers.InvalidHyperParameterException;
import NueralNetworkLibrary.ExceptionHandlers.ShapeMismatchException;

public final class Validator {

    // Prevent instantiation (utility class pattern)
    private Validator() {}

    /*   ==================
         MATRIX VALIDATIONS
         ==================   */

    // Validate same shape (used in add, subtract, etc.)
    public static void validateSameShape(Matrix a, Matrix b, String operation) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("Matrices cannot be null.");
        }

        if (a.getRows() != b.getRows() || a.getCols() != b.getCols()) {
            throw new ShapeMismatchException(operation + " requires matrices of the same shape.");
        }
    }

    // Validate dot product compatibility
    public static void validateDot(Matrix a, Matrix b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("Matrices cannot be null.");
        }

        if (a.getCols() != b.getRows()) {
            throw new ShapeMismatchException("Dot product requires A.cols == B.rows.");
        }
    }

    // Validate matrix dimensions are positive
    public static void validateMatrixDimensions(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Matrix dimensions must be positive.");
        }
    }

    // Validate matrix contains no NaN or Infinity
    public static void validateFinite(Matrix matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("Matrix cannot be null.");
        }

        for (double[] row : matrix.getData()) {
            for (double value : row) {
                if (Double.isNaN(value) || Double.isInfinite(value)) {
                    throw new IllegalArgumentException(
                            "Matrix contains NaN or Infinite values."
                    );
                }
            }
        }
    }

    /* =========================
       DATASET VALIDATIONS
       ========================= */

    // Validate dataset consistency
    public static void validateDataset(Dataset dataset) {
        if (dataset == null) {
            throw new IllegalArgumentException("Dataset cannot be null.");
        }

        if (dataset.getFeatures().getRows() != dataset.getLabels().getRows()) {
            throw new ShapeMismatchException(
                    "Features and labels must have the same number of samples."
            );
        }
    }

    // Validate train-test split ratio
    public static void validateSplitRatio(double ratio) {
        if (ratio <= 0 || ratio >= 1 || Double.isNaN(ratio)) {
            throw new IllegalArgumentException(
                    "Split ratio must be between 0 and 1."
            );
        }
    }

    /* =========================
       HYPERPARAMETER VALIDATIONS
       ========================= */

    // Validate learning rate
    public static void validateLearningRate(double learningRate) {
        if (learningRate <= 0 ||
                Double.isNaN(learningRate) ||
                Double.isInfinite(learningRate)) {

            throw new InvalidHyperParameterException("Learning rate must be a positive finite number.");
        }
    }

    // Validate number of epochs
    public static void validateEpochs(int epochs) {
        if (epochs <= 0) {
            throw new InvalidHyperParameterException("Number of epochs must be positive.");
        }
    }

    // Validate batch size
    public static void validateBatchSize(int batchSize, int datasetSize) {
        if (batchSize <= 0 || batchSize > datasetSize) {
            throw new InvalidHyperParameterException("Invalid batch size.");
        }
    }
}

