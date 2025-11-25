package FuzzyLogicLibrary;

public class Normalization {
    /** Clamp a value to [0,1]. */
    public static double clamp01(double v) {
        if (Double.isNaN(v)) return 0.0;
        if (v < 0.0) return 0.0;
        if (v > 1.0) return 1.0;
        return v;
    }

    /**
     * Return a new array scaled so the sum equals 1. If sum is zero or array is null/empty, returns a copy.
     */
    public static double[] normalizeToUnitSum(double[] values) {
        if (values == null || values.length == 0) return values;
        double sum = 0.0;
        for (double v : values) {
            sum += v;
        }
        double[] out = new double[values.length];
        if (sum == 0.0 || !Double.isFinite(sum)) {
            System.arraycopy(values, 0, out, 0, values.length);
            return out;
        }
        for (int i = 0; i < values.length; i++) out[i] = values[i] / sum;
        return out;
    }

    /**
     * Min-max scale array to [0,1]. If all values equal or invalid, returns zeros array of same length.
     */
    public static double[] scaleTo01(double[] values) {
        if (values == null || values.length == 0) return values;
        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;
        for (double v : values) {
            if (v < min) min = v;
            if (v > max) max = v;
        }
        double[] out = new double[values.length];
        double range = max - min;
        if (!(range > 0.0) || !Double.isFinite(range)) {
            // all equal or invalid -> zeros
            return out;
        }
        for (int i = 0; i < values.length; i++) out[i] = (values[i] - min) / range;
        return out;
    }
}
