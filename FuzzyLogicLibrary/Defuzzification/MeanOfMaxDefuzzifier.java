package FuzzyLogicLibrary.Defuzzification;

public class MeanOfMaxDefuzzifier implements DefuzzificationMethod {
    private static final double EPS = 1e-12;

    /**
     * Mean of maxima: computes the average of x values where membership reaches its maximum.
     * Returns NaN if all memberships are zero (no activation).
     */
    @Override
    public double defuzzify(double[] universe, double[] memberships) {
        FuzzyLogicLibrary.Validation.requireNonEmpty(universe, "universe");
        FuzzyLogicLibrary.Validation.requireSameLength(universe, memberships, "universe", "memberships");
        FuzzyLogicLibrary.Validation.requireStrictlyIncreasing(universe, "universe");

        double max = 0.0;
        for (double m : memberships) {
            double mu = FuzzyLogicLibrary.Normalization.clamp01(m);
            if (mu > max) max = mu;
        }
        if (max <= EPS) return Double.NaN;

        double sumX = 0.0;
        int count = 0;
        for (int i = 0; i < memberships.length; i++) {
            double mu = FuzzyLogicLibrary.Normalization.clamp01(memberships[i]);
            if (Math.abs(mu - max) <= EPS) {
                sumX += universe[i];
                count++;
            }
        }
        return count == 0 ? Double.NaN : (sumX / count);
    }
}
