package FuzzyLogicLibrary.Rules;

import FuzzyLogicLibrary.Inference.InferenceEngine;
import FuzzyLogicLibrary.Operators.AndOperand;
import FuzzyLogicLibrary.Operators.OrOperand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RuleBase {
    private final List<FuzzyRule> rules = new ArrayList<>();

    public void addRule(FuzzyRule rule) {
        if (rule == null) throw new IllegalArgumentException("rule cannot be null");
        rules.add(rule);
    }

    public List<FuzzyRule> getRules() {
        return Collections.unmodifiableList(rules);
    }

    // Editor API
    public int size() { return rules.size(); }
    public FuzzyRule getRule(int index) { return rules.get(index); }
    public void removeRule(int index) { rules.remove(index); }
    public void clear() { rules.clear(); }
    public void setRuleEnabled(int index, boolean enabled) { rules.get(index).setEnabled(enabled); }
    public void setRuleWeight(int index, double weight) { rules.get(index).setWeight(weight); }
    public void replaceRule(int index, FuzzyRule newRule) {
        if (newRule == null) throw new IllegalArgumentException("newRule cannot be null");
        rules.set(index, newRule);
    }

    /** Build Mamdani rule activations from fuzzified memberships. */
    public List<InferenceEngine.RuleActivation> evaluateMamdaniActivations(Map<String, Double> membershipsBySetName,
                                                                          AndOperand andOp,
                                                                          OrOperand orOp) {
        List<InferenceEngine.RuleActivation> activations = new ArrayList<>();
        for (FuzzyRule r : rules) {
            if (!r.getConsequent().isMamdani()) continue;
            double w = r.fireStrength(membershipsBySetName, andOp, orOp);
            if (w > 0.0) {
                activations.add(r.toActivation(w));
            }
        }
        return activations;
    }

    /** Container for Sugeno zero-order contribution */
    public static class SugenoContribution {
        public final double weight; // rule firing strength
        public final double value;  // consequent constant
        public SugenoContribution(double weight, double value) {
            this.weight = weight;
            this.value = value;
        }
    }

    /** Collect Sugeno zero-order contributions (weight, constant value) for weighted average. */
    public List<SugenoContribution> evaluateSugenoZeroOrder(Map<String, Double> membershipsBySetName,
                                                            AndOperand andOp,
                                                            OrOperand orOp) {
        List<SugenoContribution> out = new ArrayList<>();
        for (FuzzyRule r : rules) {
            if (!r.getConsequent().isSugenoConstant()) continue;
            double w = r.fireStrength(membershipsBySetName, andOp, orOp);
            if (w > 0.0) {
                out.add(new SugenoContribution(w, r.getConsequent().getSugenoConstantOrNull()));
            }
        }
        return out;
    }
}
