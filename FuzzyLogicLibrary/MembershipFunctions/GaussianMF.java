package FuzzyLogicLibrary.MembershipFunctions;

public class GaussianMF implements MembershipFunction {

    private final double mean;
    private final double sigma;  //Standard Deviation

    public GaussianMF(double mean, double sigma) {
        if (sigma <= 0)
            throw new IllegalArgumentException("sigma must be > 0");

        this.mean = mean;
        this.sigma = sigma;
    }

    @Override
    public double compute(double x) {
        double exponent = -0.5 * Math.pow((x - mean) / sigma, 2);
        return Math.exp(exponent);
    }

    @Override
    public double getStart() { return mean - 4 * sigma; }

    @Override
    public double getEnd() { return mean + 4 * sigma; }
}
