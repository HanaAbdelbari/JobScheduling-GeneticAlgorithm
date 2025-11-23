package FuzzyLogicLibrary.MembershipFunctions;

public class TriangularMF implements MembershipFunction {

    private final double a; // left
    private final double b; // center (peak)
    private final double c; // right

    public TriangularMF(double a, double b, double c) {
        if (!(a <= b && b <= c))
            throw new IllegalArgumentException("This Set is not a valid triangle");

        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double compute(double x) {
        if (x <= a || x >= c) return 0.0;
        if (x == b) return 1.0;
        if (x < b) return (x - a) / (b - a);
        return (c - x) / (c - b);
    }

    @Override
    public double getStart() { return a; }

    @Override
    public double getEnd() { return c; }
}
