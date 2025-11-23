package FuzzyLogicLibrary.Operators;

public class ProductAnd implements AndOperand {
    @Override
    public double and(double a, double b) {
        return a * b;
    }
}
