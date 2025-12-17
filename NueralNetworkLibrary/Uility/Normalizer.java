package NeuralNetworkLibrary.Utility;

import NueralNetworkLibrary.ExceptionHandlers.InvalidHyperParameterException;

public class Normalizer {

    // Min-Max Normalization
    public static Matrix minMaxNormalize(Matrix matrix) {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;

        double[][] data = matrix.getData();

        for (double[] row : data) {
            for (double value : row) {
                if (value < min) min = value;
                if (value > max) max = value;
            }
        }

        if (max == min) {
            throw new InvalidHyperParameterException("Cannot normalize matrix with constant values.");
        }

        Matrix result = new Matrix(matrix.getRows(), matrix.getCols());

        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getCols(); j++) {
                result.getData()[i][j] =
                        (data[i][j] - min) / (max - min);
            }
        }

        return result;
    }

// Z-score normalizer
public static Matrix zScoreNormalize(Matrix matrix) {
    double sum = 0;
    int count = matrix.getRows() * matrix.getCols();

    for (double[] row : matrix.getData()) {
        for (double value : row) {
            sum += value;
        }
    }

    double mean = sum / count;

    double variance = 0;
    for (double[] row : matrix.getData()) {
        for (double value : row) {
            variance += Math.pow(value - mean, 2);
        }
    }

    double std = Math.sqrt(variance / count);

    if (std == 0) {
        throw new InvalidHyperParameterException("Standard deviation cannot be zero.");
    }

    Matrix result = new Matrix(matrix.getRows(), matrix.getCols());

    for (int i = 0; i < matrix.getRows(); i++) {
        for (int j = 0; j < matrix.getCols(); j++) {
            result.getData()[i][j] =
                    (matrix.getData()[i][j] - mean) / std;
        }
    }

    return result;
}

}
