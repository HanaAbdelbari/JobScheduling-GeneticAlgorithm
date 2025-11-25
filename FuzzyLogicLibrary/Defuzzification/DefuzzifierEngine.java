package FuzzyLogicLibrary.Defuzzification;

public class DefuzzifierEngine {
    /**
     * Defuzzify a Mamdani aggregated output using the provided method.
     */
    public static double defuzzifyMamdani(double[] universe, double[] memberships, DefuzzificationMethod method) {
        if (method == null) throw new IllegalArgumentException("defuzzification method cannot be null");
        return method.defuzzify(universe, memberships);
    }

    /**
     * Defuzzify Sugeno zero-order (constant) contributions using the provided method.
     */
    public static double defuzzifySugeno(java.util.List<FuzzyLogicLibrary.Rules.RuleBase.SugenoContribution> contributions,
                                         DefuzzificationMethod method) {
        if (method == null) throw new IllegalArgumentException("defuzzification method cannot be null");
        return method.defuzzify(contributions);
    }

    /**
     * Convenience: centroid for Mamdani.
     */
    public static double centroid(double[] universe, double[] memberships) {
        return new CentroidDefuzzifier().defuzzify(universe, memberships);
    }

    /**
     * Convenience: mean of maxima for Mamdani.
     */
    public static double meanOfMax(double[] universe, double[] memberships) {
        return new MeanOfMaxDefuzzifier().defuzzify(universe, memberships);
    }

    /**
     * Convenience: weighted average for Sugeno zero-order.
     */
    public static double weightedAverage(java.util.List<FuzzyLogicLibrary.Rules.RuleBase.SugenoContribution> contributions) {
        return new WeightedAverageDefuzzifier().defuzzify(contributions);
    }
}
