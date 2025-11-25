package FuzzyLogicLibrary.Inference;

public interface InferenceEngine {
    /**
     * Holder for a single rule activation result used by the inference engine.
     * It represents a fired rule with a certain strength on a specific consequent fuzzy set.
     */
    public static class RuleActivation {
        public final FuzzyLogicLibrary.Variables.FuzzySet consequentSet;
        public final double strength; // rule firing strength in [0,1]

        public RuleActivation(FuzzyLogicLibrary.Variables.FuzzySet consequentSet, double strength) {
            if (consequentSet == null)
                throw new IllegalArgumentException("consequentSet cannot be null");
            if (strength < 0.0 || strength > 1.0)
                throw new IllegalArgumentException("strength must be in [0,1]");
            this.consequentSet = consequentSet;
            this.strength = strength;
        }
    }

    /** Set implication operator (e.g., min or product). */
    void setImplicationOperator(ImplicationOperator implicationOperator);

    /** Set aggregation operator over rules (e.g., max or sum-bounded). */
    void setAggregationOperator(AggregationOperator aggregationOperator);

    /** Get current implication operator. */
    ImplicationOperator getImplicationOperator();

    /** Get current aggregation operator. */
    AggregationOperator getAggregationOperator();

    /**
     * Perform Mamdani-style inference over an output universe given a list of rule activations.
     * The engine applies implication to each rule's consequent MF by its strength and then
     * aggregates all rules' contributions per universe sample.
     *
     * @param activations list of fired rules described by their strength and consequent set
     * @param outputUniverse points in the output universe of discourse
     * @return aggregated membership values for each x in outputUniverse (same length)
     */
    double[] infer(java.util.List<RuleActivation> activations, double[] outputUniverse);
}
