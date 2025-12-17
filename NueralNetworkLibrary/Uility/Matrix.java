package NueralNetworkLibrary.Uility;

import NueralNetworkLibrary.ExceptionHandlers.ShapeMismatchException;

import java.util.function.Function;

public class Matrix {

    private final int rows;
    private final int cols;
    private final double[][] data;

    // Constructor
    public Matrix(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new ShapeMismatchException("Matrix dimensions must be positive.");
        }
        this.rows = rows;
        this.cols = cols;
        this.data = new double[rows][cols];
    }

    // Constructor from 2D array
    public Matrix(double[][] data) {
        if (data == null || data.length == 0) {
            throw new IllegalArgumentException("Data cannot be null or empty.");  //Passing null is developer error, not matrix logic error
        }
        this.rows = data.length;
        this.cols = data[0].length;
        this.data = data;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public double[][] getData() {
        return data;
    }


// Matrix Addition
    public Matrix add(Matrix other) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new ShapeMismatchException("Matrix addition shape mismatch.");
        }

        Matrix result = new Matrix(rows, cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.data[i][j] = this.data[i][j] + other.data[i][j];
            }
        }
        return result;
    }


// Matrix Substraction
    public Matrix subtract(Matrix other) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new ShapeMismatchException("Matrix subtraction shape mismatch.");
        }

        Matrix result = new Matrix(rows, cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.data[i][j] = this.data[i][j] - other.data[i][j];
            }
        }
        return result;
    }

// Matrix Dot Product --> the rule is (A.cols == B.rows)
public Matrix dot(Matrix other) {
    if (this.cols != other.rows) {
        throw new ShapeMismatchException("Matrix dot product shape mismatch.");
    }

    Matrix result = new Matrix(this.rows, other.cols);

    for (int i = 0; i < this.rows; i++) {
        for (int j = 0; j < other.cols; j++) {
            double sum = 0;
            for (int k = 0; k < this.cols; k++) {
                sum += this.data[i][k] * other.data[k][j];
            }
            result.data[i][j] = sum;
        }
    }
    return result;
}

//Matrix Transpose
public Matrix transpose() {
    Matrix result = new Matrix(cols, rows);

    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            result.data[j][i] = this.data[i][j];
        }
    }
    return result;
}

// Element-Wise Function Application
public Matrix apply(Function<Double, Double> function) {
    Matrix result = new Matrix(rows, cols);

    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            result.data[i][j] = function.apply(this.data[i][j]);
        }
    }
    return result;
}

}
