package FuzzyLogicLibrary.Defuzzification;

public class WeightedAverageDefuzzifier implements DefuzzificationMethod {
    /**
     * Weighted average for Sugeno zero-order model: sum(w_i * v_i) / sum(w_i).
     * Returns NaN if the sum of weights is zero (no activation).
     */
    @Override
    public double defuzzify(java.util.List<FuzzyLogicLibrary.Rules.RuleBase.SugenoContribution> contributions) {
        if (contributions == null || contributions.isEmpty()) return Double.NaN;

        double num = 0.0;
        double den = 0.0;
        for (FuzzyLogicLibrary.Rules.RuleBase.SugenoContribution c : contributions) {
            double w = Math.max(0.0, c.weight);
            double v = c.value;
            if (!Double.isFinite(v)) continue;
            num += w * v;
            den += w;
        }
        return den == 0.0 ? Double.NaN : (num / den);
    }
}
