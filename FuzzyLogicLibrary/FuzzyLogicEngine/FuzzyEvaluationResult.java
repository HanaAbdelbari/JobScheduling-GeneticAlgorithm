package FuzzyLogicLibrary.FuzzyLogicEngine;

import FuzzyLogicLibrary.Inference.InferenceEngine;
import FuzzyLogicLibrary.Rules.RuleBase;

import java.util.List;
import java.util.Map;

public class FuzzyEvaluationResult {
    // Common
    private final Map<String, Double> membershipsBySet;
    private final double crisp;

    // Mamdani-specific
    private final double[] ruleStrengths; // per rule in rule base order
    private final boolean[] ruleEnabled;  // per rule
    private final String[] ruleConsequents; // name or label
    private final List<InferenceEngine.RuleActivation> activations;
    private final double[] universe;
    private final double[] aggregated;

    // Sugeno-specific
    private final List<RuleBase.SugenoContribution> contributions;

    private FuzzyEvaluationResult(Map<String, Double> membershipsBySet,
                                  double crisp,
                                  double[] ruleStrengths,
                                  boolean[] ruleEnabled,
                                  String[] ruleConsequents,
                                  List<InferenceEngine.RuleActivation> activations,
                                  double[] universe,
                                  double[] aggregated,
                                  List<RuleBase.SugenoContribution> contributions) {
        this.membershipsBySet = membershipsBySet;
        this.crisp = crisp;
        this.ruleStrengths = ruleStrengths;
        this.ruleEnabled = ruleEnabled;
        this.ruleConsequents = ruleConsequents;
        this.activations = activations;
        this.universe = universe;
        this.aggregated = aggregated;
        this.contributions = contributions;
    }

    public static FuzzyEvaluationResult mamdani(Map<String, Double> membershipsBySet,
                                                double[] ruleStrengths,
                                                boolean[] ruleEnabled,
                                                String[] ruleConsequents,
                                                List<InferenceEngine.RuleActivation> activations,
                                                double[] universe,
                                                double[] aggregated,
                                                double crisp) {
        return new FuzzyEvaluationResult(membershipsBySet, crisp, ruleStrengths, ruleEnabled, ruleConsequents,
                activations, universe, aggregated, null);
    }

    public static FuzzyEvaluationResult sugeno(Map<String, Double> membershipsBySet,
                                               List<RuleBase.SugenoContribution> contributions,
                                               double crisp) {
        return new FuzzyEvaluationResult(membershipsBySet, crisp, null, null, null,
                null, null, null, contributions);
    }

    // Getters
    public Map<String, Double> getMembershipsBySet() { return membershipsBySet; }
    public double getCrisp() { return crisp; }

    public double[] getRuleStrengths() { return ruleStrengths; }
    public boolean[] getRuleEnabled() { return ruleEnabled; }
    public String[] getRuleConsequents() { return ruleConsequents; }
    public List<InferenceEngine.RuleActivation> getActivations() { return activations; }
    public double[] getUniverse() { return universe; }
    public double[] getAggregated() { return aggregated; }

    public List<RuleBase.SugenoContribution> getContributions() { return contributions; }
}
