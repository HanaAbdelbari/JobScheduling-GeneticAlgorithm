package FuzzyLogicLibrary;

public class Validation {
    public static void requireNonEmpty(double[] arr, String name) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException(name + " must be non-null and non-empty");
        }
        for (double v : arr) {
            if (!Double.isFinite(v)) {
                throw new IllegalArgumentException(name + " contains non-finite values");
            }
        }
    }

    public static void requireSameLength(double[] a, double[] b, String aName, String bName) {
        if (a == null || b == null) {
            throw new IllegalArgumentException(aName + " and " + bName + " must be non-null");
        }
        if (a.length != b.length) {
            throw new IllegalArgumentException(aName + " and " + bName + " must have same length");
        }
    }

    public static void requireStrictlyIncreasing(double[] arr, String name) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException(name + " must be non-null and non-empty");
        }
        double prev = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (!(arr[i] > prev)) {
                throw new IllegalArgumentException(name + " must be strictly increasing");
            }
            prev = arr[i];
        }
    }
}
