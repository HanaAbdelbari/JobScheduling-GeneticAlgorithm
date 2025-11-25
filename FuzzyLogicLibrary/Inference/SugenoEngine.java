package FuzzyLogicLibrary.Inference;

import java.util.List;

public class SugenoEngine implements InferenceEngine {
    private ImplicationOperator implicationOperator;
    private AggregationOperator aggregationOperator;

    public SugenoEngine() {
        // Typical defaults for Sugeno-like behavior
        this.implicationOperator = new ProductImplication();
        this.aggregationOperator = new SumAggregation();
    }

    public SugenoEngine(ImplicationOperator implicationOperator, AggregationOperator aggregationOperator) {
        setImplicationOperator(implicationOperator);
        setAggregationOperator(aggregationOperator);
    }

    /**
     * Set implication operator (e.g., min or product).
     */
    @Override
    public void setImplicationOperator(ImplicationOperator implicationOperator) {
        if (implicationOperator == null)
            throw new IllegalArgumentException("implicationOperator cannot be null");
        this.implicationOperator = implicationOperator;
    }

    /**
     * Set aggregation operator over rules (e.g., max or sum-bounded).
     */
    @Override
    public void setAggregationOperator(AggregationOperator aggregationOperator) {
        if (aggregationOperator == null)
            throw new IllegalArgumentException("aggregationOperator cannot be null");
        this.aggregationOperator = aggregationOperator;
    }

    /** Get current implication operator. */
    @Override
    public ImplicationOperator getImplicationOperator() {
        return implicationOperator;
    }

    /** Get current aggregation operator. */
    @Override
    public AggregationOperator getAggregationOperator() {
        return aggregationOperator;
    }

    /**
     * Until Sugeno-specific consequents (functions or constants) are added to the Rules layer,
     * we mirror the Mamdani-like aggregation over the provided consequent sets.
     */
    @Override
    public double[] infer(List<RuleActivation> activations, double[] outputUniverse) {
        if (outputUniverse == null || outputUniverse.length == 0)
            throw new IllegalArgumentException("outputUniverse must be non-empty");

        double[] aggregated = new double[outputUniverse.length];
        if (activations == null || activations.isEmpty()) {
            return aggregated;
        }

        for (int i = 0; i < outputUniverse.length; i++) {
            double x = outputUniverse[i];
            double acc = 0.0;
            boolean first = true;

            for (RuleActivation ra : activations) {
                double mfVal = ra.consequentSet.computeMembership(x);
                double implied = implicationOperator.imply(ra.strength, mfVal);

                if (first) {
                    acc = implied;
                    first = false;
                } else {
                    acc = aggregationOperator.aggregate(acc, implied);
                }
            }
            aggregated[i] = Math.max(0.0, Math.min(1.0, acc));
        }

        return aggregated;
    }
}
