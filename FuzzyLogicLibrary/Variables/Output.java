package FuzzyLogicLibrary.Variables;

public class Output extends LinguisticVariable {

    private final double min;
    private final double max;
    private final int resolution; // number of samples for defuzzification

    public Output(String name, double min, double max, int resolution) {
        super(name);

        if (min >= max)
            throw new IllegalArgumentException("OutputVariable: min < max required");
        if (resolution <= 10)
            throw new IllegalArgumentException("Resolution should be reasonable (e.g., ≥ 50)");

        this.min = min;
        this.max = max;
        this.resolution = resolution;
    }

    public double[] getUniverse() {
        double[] universe = new double[resolution];
        double step = (max - min) / (resolution - 1);

        for (int i = 0; i < resolution; i++) {
            universe[i] = min + i * step;
        }
        return universe;
    }

    public double getMin() { return min; }
    public double getMax() { return max; }
}
