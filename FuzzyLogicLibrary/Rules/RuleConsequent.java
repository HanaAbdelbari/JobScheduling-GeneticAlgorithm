package FuzzyLogicLibrary.Rules;

import FuzzyLogicLibrary.Variables.FuzzySet;

public class RuleConsequent {
    private final FuzzySet outputSet;        // for Mamdani
    private final Double sugenoConstant;     // for Sugeno zero-order (constant) model

    /** Mamdani-style consequent pointing to an output fuzzy set. */
    public static RuleConsequent mamdani(FuzzySet outputSet) {
        if (outputSet == null) throw new IllegalArgumentException("outputSet cannot be null");
        return new RuleConsequent(outputSet, null);
    }

    /** Sugeno zero-order consequent using a constant crisp value. */
    public static RuleConsequent sugenoConstant(double value) {
        return new RuleConsequent(null, value);
    }

    private RuleConsequent(FuzzySet outputSet, Double sugenoConstant) {
        this.outputSet = outputSet;
        this.sugenoConstant = sugenoConstant;
    }

    public boolean isMamdani() { return outputSet != null; }
    public boolean isSugenoConstant() { return sugenoConstant != null; }

    public FuzzySet getOutputSet() { return outputSet; }
    public Double getSugenoConstantOrNull() { return sugenoConstant; }
}
