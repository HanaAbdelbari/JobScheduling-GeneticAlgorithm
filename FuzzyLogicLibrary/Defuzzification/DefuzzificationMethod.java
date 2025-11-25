package FuzzyLogicLibrary.Defuzzification;

public interface DefuzzificationMethod {
    /**
     * Defuzzify a Mamdani aggregated membership function over an output universe.
     * Implementations that do not support this should leave it unimplemented and rely on the default which throws.
     *
     * @param universe strictly increasing sample points in the output universe
     * @param memberships membership values in [0,1] same length as universe
     * @return crisp value
     */
    default double defuzzify(double[] universe, double[] memberships) {
        throw new UnsupportedOperationException("This defuzzifier does not support Mamdani arrays");
    }

    /**
     * Defuzzify a Sugeno zero-order model represented by (weight, value) contributions per rule.
     * Implementations that do not support this should leave it unimplemented and rely on the default which throws.
     *
     * @param contributions list of (weight, value) pairs
     * @return crisp value
     */
    default double defuzzify(java.util.List<FuzzyLogicLibrary.Rules.RuleBase.SugenoContribution> contributions) {
        throw new UnsupportedOperationException("This defuzzifier does not support Sugeno contributions");
    }
}
