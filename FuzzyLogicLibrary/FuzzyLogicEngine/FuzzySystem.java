package FuzzyLogicLibrary.FuzzyLogicEngine;

import FuzzyLogicLibrary.Defuzzification.DefuzzificationMethod;
import FuzzyLogicLibrary.Defuzzification.DefuzzifierEngine;
import FuzzyLogicLibrary.Defuzzification.CentroidDefuzzifier;
import FuzzyLogicLibrary.Defuzzification.WeightedAverageDefuzzifier;
import FuzzyLogicLibrary.Inference.InferenceEngine;
import FuzzyLogicLibrary.Inference.MamdaniEngine;
import FuzzyLogicLibrary.Inference.SugenoEngine;
import FuzzyLogicLibrary.Operators.AndOperand;
import FuzzyLogicLibrary.Operators.OrOperand;
import FuzzyLogicLibrary.Operators.MinAnd;
import FuzzyLogicLibrary.Operators.MaxOr;
import FuzzyLogicLibrary.Inference.ImplicationOperator;
import FuzzyLogicLibrary.Inference.AggregationOperator;
import FuzzyLogicLibrary.Rules.RuleBase;
import FuzzyLogicLibrary.Variables.Input;
import FuzzyLogicLibrary.Variables.Output;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FuzzySystem {
    // Model elements
    private final Map<String, Input> inputs = new HashMap<>();
    private Output output;
    private RuleBase ruleBase;

    // Operators and engines
    private AndOperand andOperand; // antecedent AND operator
    private OrOperand orOperand;   // antecedent OR operator
    private InferenceEngine mamdaniEngine;
    private InferenceEngine sugenoEngine;

    public FuzzySystem() {
        // Reasonable defaults
        this.mamdaniEngine = new MamdaniEngine();
        this.sugenoEngine = new SugenoEngine();
        this.andOperand = new MinAnd();
        this.orOperand = new MaxOr();
    }

    // Configuration
    public void addInput(Input var) {
        if (var == null) throw new IllegalArgumentException("input cannot be null");
        inputs.put(var.getName(), var);
    }

    public void setOutput(Output output) {
        if (output == null) throw new IllegalArgumentException("output cannot be null");
        this.output = output;
    }

    public void setRuleBase(RuleBase ruleBase) {
        if (ruleBase == null) throw new IllegalArgumentException("ruleBase cannot be null");
        this.ruleBase = ruleBase;
    }

    public void setAndOperand(AndOperand andOperand) {
        if (andOperand == null) throw new IllegalArgumentException("andOperand cannot be null");
        this.andOperand = andOperand;
    }

    public void setOrOperand(OrOperand orOperand) {
        if (orOperand == null) throw new IllegalArgumentException("orOperand cannot be null");
        this.orOperand = orOperand;
    }

    public void setMamdaniEngine(InferenceEngine engine) {
        if (engine == null) throw new IllegalArgumentException("engine cannot be null");
        this.mamdaniEngine = engine;
    }

    public void setSugenoEngine(InferenceEngine engine) {
        if (engine == null) throw new IllegalArgumentException("engine cannot be null");
        this.sugenoEngine = engine;
    }

    // Convenience: set operators on engines
    public void setMamdaniImplicationOperator(ImplicationOperator op) {
        ((MamdaniEngine)mamdaniEngine).setImplicationOperator(op);
    }
    public void setMamdaniAggregationOperator(AggregationOperator op) {
        ((MamdaniEngine)mamdaniEngine).setAggregationOperator(op);
    }
    public void setSugenoImplicationOperator(ImplicationOperator op) {
        ((SugenoEngine)sugenoEngine).setImplicationOperator(op);
    }
    public void setSugenoAggregationOperator(AggregationOperator op) {
        ((SugenoEngine)sugenoEngine).setAggregationOperator(op);
    }

    // Workflow helpers
    private Map<String, Double> fuzzifyAll(Map<String, Double> crispInputs) {
        if (crispInputs == null) throw new IllegalArgumentException("crispInputs cannot be null");
        Map<String, Double> membershipsBySet = new HashMap<>();
        for (Map.Entry<String, Double> e : crispInputs.entrySet()) {
            Input var = inputs.get(e.getKey());
            if (var == null)
                throw new IllegalArgumentException("Unknown input variable: " + e.getKey());
            Map<String, Double> m = var.fuzzify(e.getValue());
            // Note: set names must be unique across variables to avoid collisions
            membershipsBySet.putAll(m);
        }
        return membershipsBySet;
    }

    private void ensureReadyForMamdani() {
        if (ruleBase == null) throw new IllegalStateException("ruleBase not set");
        if (output == null) throw new IllegalStateException("output not set");
        if (andOperand == null) throw new IllegalStateException("andOperand not set");
        if (orOperand == null) throw new IllegalStateException("orOperand not set");
        if (mamdaniEngine == null) throw new IllegalStateException("mamdaniEngine not set");
    }

    private void ensureReadyForSugeno() {
        if (ruleBase == null) throw new IllegalStateException("ruleBase not set");
        if (andOperand == null) throw new IllegalStateException("andOperand not set");
        if (orOperand == null) throw new IllegalStateException("orOperand not set");
        if (sugenoEngine == null) throw new IllegalStateException("sugenoEngine not set");
    }

    // Public API
    /**
     * Run a full Mamdani pipeline: fuzzification -> rule evaluation -> inference -> defuzzification.
     * @param crispInputs map from input variable name to crisp value
     * @param defuzzifier a Mamdani-compatible defuzzifier (e.g., CentroidDefuzzifier, MeanOfMaxDefuzzifier)
     * @return crisp output value (may be NaN if no activation)
     */
    public double evaluateMamdani(Map<String, Double> crispInputs, DefuzzificationMethod defuzzifier) {
        ensureReadyForMamdani();
        if (defuzzifier == null) throw new IllegalArgumentException("defuzzifier cannot be null");

        Map<String, Double> membershipsBySet = fuzzifyAll(crispInputs);
        List<InferenceEngine.RuleActivation> activations = ruleBase.evaluateMamdaniActivations(membershipsBySet, andOperand, orOperand);

        double[] universe = output.getUniverse();
        double[] aggregated = mamdaniEngine.infer(activations, universe);
        return DefuzzifierEngine.defuzzifyMamdani(universe, aggregated, defuzzifier);
    }

    /** Overload using default centroid defuzzifier. */
    public double evaluateMamdani(Map<String, Double> crispInputs) {
        return evaluateMamdani(crispInputs, new CentroidDefuzzifier());
    }

    /**
     * Run a full Sugeno zero-order pipeline: fuzzification -> rule contributions -> weighted average.
     * If no defuzzifier is provided, uses WeightedAverageDefuzzifier by default.
     * @param crispInputs map from input variable name to crisp value
     * @param optionalDefuzzifier optional Sugeno defuzzifier (weighted average)
     * @return crisp output value (may be NaN if no activation)
     */
    public double evaluateSugenoZeroOrder(Map<String, Double> crispInputs, DefuzzificationMethod optionalDefuzzifier) {
        ensureReadyForSugeno();
        Map<String, Double> membershipsBySet = fuzzifyAll(crispInputs);
        List<RuleBase.SugenoContribution> contributions = ruleBase.evaluateSugenoZeroOrder(membershipsBySet, andOperand, orOperand);

        DefuzzificationMethod method = optionalDefuzzifier != null ? optionalDefuzzifier : new WeightedAverageDefuzzifier();
        return DefuzzifierEngine.defuzzifySugeno(contributions, method);
    }

    // Detailed evaluation results for debugging/visualization
    public FuzzyEvaluationResult evaluateMamdaniDetailed(Map<String, Double> crispInputs, DefuzzificationMethod defuzzifier) {
        ensureReadyForMamdani();
        if (defuzzifier == null) throw new IllegalArgumentException("defuzzifier cannot be null");
        Map<String, Double> membershipsBySet = fuzzifyAll(crispInputs);

        // Rule strengths
        List<FuzzyLogicLibrary.Rules.FuzzyRule> rules = ruleBase.getRules();
        double[] strengths = new double[rules.size()];
        boolean[] enableds = new boolean[rules.size()];
        String[] consequents = new String[rules.size()];
        for (int i = 0; i < rules.size(); i++) {
            var r = rules.get(i);
            strengths[i] = r.fireStrength(membershipsBySet, andOperand, orOperand);
            enableds[i] = r.isEnabled();
            consequents[i] = r.getConsequent().isMamdani() ? r.getConsequent().getOutputSet().getName() : "(Sugeno)";
        }

        List<InferenceEngine.RuleActivation> activations = ruleBase.evaluateMamdaniActivations(membershipsBySet, andOperand, orOperand);
        double[] universe = output.getUniverse();
        double[] aggregated = mamdaniEngine.infer(activations, universe);
        double crisp = DefuzzifierEngine.defuzzifyMamdani(universe, aggregated, defuzzifier);

        return FuzzyEvaluationResult.mamdani(membershipsBySet, strengths, enableds, consequents, activations, universe, aggregated, crisp);
    }

    public FuzzyEvaluationResult evaluateSugenoDetailed(Map<String, Double> crispInputs, DefuzzificationMethod optionalDefuzzifier) {
        ensureReadyForSugeno();
        Map<String, Double> membershipsBySet = fuzzifyAll(crispInputs);
        List<RuleBase.SugenoContribution> contributions = ruleBase.evaluateSugenoZeroOrder(membershipsBySet, andOperand, orOperand);
        DefuzzificationMethod method = optionalDefuzzifier != null ? optionalDefuzzifier : new WeightedAverageDefuzzifier();
        double crisp = DefuzzifierEngine.defuzzifySugeno(contributions, method);
        // No aggregated curve for Sugeno zero-order in this simple path
        return FuzzyEvaluationResult.sugeno(membershipsBySet, contributions, crisp);
    }
}
