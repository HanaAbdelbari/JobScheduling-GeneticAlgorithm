package FuzzyLogicLibrary.Operators;

public class MinAnd implements AndOperand {
    @Override
    public double and(double a, double b) {
        return Math.min(a, b);
    }
}
