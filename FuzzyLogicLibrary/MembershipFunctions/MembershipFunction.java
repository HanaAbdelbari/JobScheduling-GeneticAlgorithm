package FuzzyLogicLibrary.MembershipFunctions;

public interface MembershipFunction {

    /**
     * Compute the membership degree μ(x).
     * @param x crisp input value
     * @return membership in [0, 1]
     */
    double compute(double x);

    /**
     * @return leftmost bound of the MF domain (optional but useful)
     */
    double getStart();

    /**
     * @return rightmost bound of the MF domain
     */
    double getEnd();
}
