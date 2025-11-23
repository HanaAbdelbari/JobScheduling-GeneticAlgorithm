package FuzzyLogicLibrary.Operators;

public class SumOr implements OrOperand {
    @Override
    public double or(double a, double b) {
        return Math.min(1.0, a + b);
    }
}
