package FuzzyLogicLibrary.Operators;

public class MaxOr implements OrOperand {
    @Override
    public double or(double a, double b) {
        return Math.max(a, b);
    }
}
