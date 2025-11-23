package FuzzyLogicLibrary.Variables;

import java.util.HashMap;
import java.util.Map;

public class Input extends LinguisticVariable {

    private final double min;
    private final double max;

    public Input(String name, double min, double max) {
        super(name);

        if (min >= max)
            throw new IllegalArgumentException("InputVariable: min must be < max");

        this.min = min;
        this.max = max;
    }

    public double clamp(double x) {
        return Math.max(min, Math.min(max, x));
    }

    /**
     * Fuzzification: For each fuzzy set, compute μ(x)
     * @param x crisp input value
     * @return map setName → membershipDegree
     */
    public Map<String, Double> fuzzify(double x) {
        x = clamp(x);

        Map<String, Double> result = new HashMap<>();
        for (FuzzySet set : fuzzySets) {
            result.put(set.getName(), set.computeMembership(x));
        }
        return result;
    }
}
