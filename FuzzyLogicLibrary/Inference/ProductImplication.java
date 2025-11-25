package FuzzyLogicLibrary.Inference;

public class ProductImplication implements ImplicationOperator {
    @Override
    public double imply(double ruleStrength, double membership) {
        return ruleStrength * membership;
    }
}
