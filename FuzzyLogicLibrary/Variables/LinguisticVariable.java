package FuzzyLogicLibrary.Variables;

import java.util.ArrayList;
import java.util.List;

public abstract class LinguisticVariable {

    protected final String name;
    protected final List<FuzzySet> fuzzySets = new ArrayList<>();

    public LinguisticVariable(String name) {
        if (name == null)
            throw new IllegalArgumentException("Variable name cannot be null");
        this.name = name;
    }

    public void addFuzzySet(FuzzySet set) {
        fuzzySets.add(set);
    }

    public List<FuzzySet> getFuzzySets() {
        return fuzzySets;
    }

    public String getName() {
        return name;
    }
}
