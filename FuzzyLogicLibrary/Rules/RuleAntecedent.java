package FuzzyLogicLibrary.Rules;

import FuzzyLogicLibrary.Operators.AndOperand;
import FuzzyLogicLibrary.Operators.OrOperand;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RuleAntecedent {

    /** One antecedent term refers to a fuzzy set by name. */
    public static class Term {
        private final String setName; // assumes unique fuzzy set names across inputs

        public Term(String setName) {
            if (setName == null)
                throw new IllegalArgumentException("setName cannot be null");
            this.setName = setName;
        }

        public String getSetName() { return setName; }
    }

    /** Operator between terms. First term has no operator before it. */
    public enum Op { AND, OR }

    private final List<Term> terms = new ArrayList<>();
    private final List<Op> ops = new ArrayList<>(); // size = max(0, terms.size()-1)

    public RuleAntecedent addTerm(String setName) {
        return addAndTerm(setName);
    }

    /** Append a term combined with AND. */
    public RuleAntecedent addAndTerm(String setName) {
        if (terms.isEmpty()) {
            terms.add(new Term(setName));
        } else {
            terms.add(new Term(setName));
            ops.add(Op.AND);
        }
        return this;
    }

    /** Append a term combined with OR. */
    public RuleAntecedent addOrTerm(String setName) {
        if (terms.isEmpty()) {
            terms.add(new Term(setName));
        } else {
            terms.add(new Term(setName));
            ops.add(Op.OR);
        }
        return this;
    }

    public List<Term> getTerms() { return terms; }
    public List<Op> getOps() { return ops; }

    /**
     * Evaluate antecedent strength by combining membership degrees of its terms
     * via the provided AndOperand/OrOperand (e.g., min/product for AND, max/sum for OR).
     *
     * @param memberships map from fuzzy set name to membership degree
     * @param andOp AND operator to combine term memberships
     * @param orOp OR operator to combine term memberships
     * @return firing strength in [0,1]
     */
    public double evaluate(Map<String, Double> memberships, AndOperand andOp, OrOperand orOp) {
        if (andOp == null) throw new IllegalArgumentException("andOp cannot be null");
        if (orOp == null) throw new IllegalArgumentException("orOp cannot be null");
        if (terms.isEmpty()) return 0.0;

        double acc = clamp01(memberships.getOrDefault(terms.get(0).getSetName(), 0.0));
        for (int i = 1; i < terms.size(); i++) {
            double v = clamp01(memberships.getOrDefault(terms.get(i).getSetName(), 0.0));
            Op op = ops.get(i - 1);
            if (op == Op.AND) acc = andOp.and(acc, v);
            else acc = orOp.or(acc, v);
        }
        return clamp01(acc);
    }

    private static double clamp01(double v) { return Math.max(0.0, Math.min(1.0, v)); }
}
