package FuzzyLogicLibrary.Variables;

import FuzzyLogicLibrary.MembershipFunctions.MembershipFunction;

public class FuzzySet {

    private final String name;
    private final MembershipFunction mf;

    public FuzzySet(String name, MembershipFunction mf) {
        if (name == null || mf == null)
            throw new IllegalArgumentException("FuzzySet name and MF cannot be null");

        this.name = name;
        this.mf = mf;
    }

    public String getName() {
        return name;
    }

    public MembershipFunction getMembershipFunction() {
        return mf;
    }

    public double computeMembership(double x) {
        return mf.compute(x);
    }

    @Override
    public String toString() {
        return "FuzzySet(" + name + ")";
    }
}
