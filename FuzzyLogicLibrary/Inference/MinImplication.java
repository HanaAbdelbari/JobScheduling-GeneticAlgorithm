package FuzzyLogicLibrary.Inference;

public class MinImplication implements ImplicationOperator {
    @Override
    public double imply(double ruleStrength, double membership) {
        return Math.min(ruleStrength, membership);
    }
}
