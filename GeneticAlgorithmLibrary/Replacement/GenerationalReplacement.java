package GeneticAlgorithmLibrary.Replacement;

import GeneticAlgorithmLibrary.Population;

public class GenerationalReplacement implements ReplacementMethod {

    @Override
    public Population replace(Population oldPopulation, Population newPopulation) {
        return newPopulation;
    }
}
