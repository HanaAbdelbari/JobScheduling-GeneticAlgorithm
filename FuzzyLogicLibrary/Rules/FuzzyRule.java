package FuzzyLogicLibrary.Rules;

import FuzzyLogicLibrary.Inference.InferenceEngine;
import FuzzyLogicLibrary.Operators.AndOperand;
import FuzzyLogicLibrary.Operators.OrOperand;

import java.util.Map;

public class FuzzyRule {
    private final RuleAntecedent antecedent;
    private final RuleConsequent consequent;
    private boolean enabled = true;
    private double weight = 1.0; // in [0,1]

    public FuzzyRule(RuleAntecedent antecedent, RuleConsequent consequent) {
        if (antecedent == null || consequent == null)
            throw new IllegalArgumentException("antecedent and consequent cannot be null");
        this.antecedent = antecedent;
        this.consequent = consequent;
    }

    public RuleAntecedent getAntecedent() { return antecedent; }
    public RuleConsequent getConsequent() { return consequent; }
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = Math.max(0.0, Math.min(1.0, weight)); }

    /** Compute rule firing strength using memberships and the given AND operator. */
    public double fireStrength(Map<String, Double> membershipsBySetName, AndOperand andOp, OrOperand orOp) {
        if (!enabled) return 0.0;
        double s = antecedent.evaluate(membershipsBySetName, andOp, orOp);
        return Math.max(0.0, Math.min(1.0, s * weight));
    }

    /** Build Mamdani RuleActivation given a firing strength. */
    public InferenceEngine.RuleActivation toActivation(double strength) {
        if (!consequent.isMamdani())
            throw new IllegalStateException("Consequent is not Mamdani type");
        return new InferenceEngine.RuleActivation(consequent.getOutputSet(), strength);
    }
}
