package FuzzyLogicLibrary.MembershipFunctions;

public class TrapezoidialMF implements MembershipFunction {

    private final double a, b, c, d;

    public TrapezoidialMF(double a, double b, double c, double d) {
        if (!(a <= b && b <= c && c <= d))
            throw new IllegalArgumentException("This Set is not a valid Trapezoid");

        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    @Override
    public double compute(double x) {
        if (x <= a || x >= d) return 0.0;
        if (x >= b && x <= c) return 1.0;
        if (x < b) return (x - a) / (b - a);
        return (d - x) / (d - c);
    }

    @Override
    public double getStart() { return a; }

    @Override
    public double getEnd() { return d; }
}
