package FuzzyLogicLibrary.Defuzzification;

public class CentroidDefuzzifier implements DefuzzificationMethod {
    /**
     * Center of gravity (centroid) for a discrete universe and aggregated memberships.
     * Returns NaN if the total membership is zero (no activation).
     */
    @Override
    public double defuzzify(double[] universe, double[] memberships) {
        FuzzyLogicLibrary.Validation.requireNonEmpty(universe, "universe");
        FuzzyLogicLibrary.Validation.requireSameLength(universe, memberships, "universe", "memberships");
        FuzzyLogicLibrary.Validation.requireStrictlyIncreasing(universe, "universe");

        double num = 0.0;
        double den = 0.0;
        for (int i = 0; i < universe.length; i++) {
            double mu = FuzzyLogicLibrary.Normalization.clamp01(memberships[i]);
            num += universe[i] * mu;
            den += mu;
        }
        return den == 0.0 ? Double.NaN : (num / den);
    }
}
