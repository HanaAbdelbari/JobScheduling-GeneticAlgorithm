package FuzzyLogicLibrary.Inference;


// to select either min/product for implication
public interface ImplicationOperator {
    double imply(double ruleStrength, double membership);
}
