package GeneticAlgorithmLibrary.Replacement;

import GeneticAlgorithmLibrary.Population;


public interface ReplacementMethod {

    Population replace(Population oldPopulation, Population newPopulation);
}
