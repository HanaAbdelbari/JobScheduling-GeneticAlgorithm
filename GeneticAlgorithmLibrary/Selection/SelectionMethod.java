package GeneticAlgorithmLibrary.Selection;

import GeneticAlgorithmLibrary.Chromosome.Chromosome;
import GeneticAlgorithmLibrary.Population;

import java.util.List;

/**
 * Interface for selection methods in genetic algorithms
 */
public interface SelectionMethod {
    List<Chromosome> select(Population population, int numParents);

    /**
     * Select parents from the population
     * @param population the current population
     * @return list of selected parent chromosomes
     */
    List<Chromosome> select(Population population);
}