package FuzzyLogicLibrary.Inference;

public class MamdaniEngine implements InferenceEngine {
    private ImplicationOperator implicationOperator;
    private AggregationOperator aggregationOperator;

    public MamdaniEngine() {
        // Defaults: classic Mamdani min for implication, max for aggregation
        this.implicationOperator = new MinImplication();
        this.aggregationOperator = new MaxAggregation();
    }

    public MamdaniEngine(ImplicationOperator implicationOperator, AggregationOperator aggregationOperator) {
        setImplicationOperator(implicationOperator);
        setAggregationOperator(aggregationOperator);
    }

    @Override
    public void setImplicationOperator(ImplicationOperator implicationOperator) {
        if (implicationOperator == null)
            throw new IllegalArgumentException("implicationOperator cannot be null");
        this.implicationOperator = implicationOperator;
    }

    @Override
    public void setAggregationOperator(AggregationOperator aggregationOperator) {
        if (aggregationOperator == null)
            throw new IllegalArgumentException("aggregationOperator cannot be null");
        this.aggregationOperator = aggregationOperator;
    }

    @Override
    public ImplicationOperator getImplicationOperator() {
        return implicationOperator;
    }

    @Override
    public AggregationOperator getAggregationOperator() {
        return aggregationOperator;
    }

    @Override
    public double[] infer(java.util.List<InferenceEngine.RuleActivation> activations, double[] outputUniverse) {
        if (outputUniverse == null || outputUniverse.length == 0)
            throw new IllegalArgumentException("outputUniverse must be non-empty");

        double[] aggregated = new double[outputUniverse.length];
        if (activations == null || activations.isEmpty()) {
            // No rules fired => zero membership everywhere
            return aggregated;
        }

        for (int i = 0; i < outputUniverse.length; i++) {
            double x = outputUniverse[i];
            double acc = 0.0; // neutral for max/sum; for max this will be replaced by aggregate()
            boolean first = true;

            for (InferenceEngine.RuleActivation ra : activations) {
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
