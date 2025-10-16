package GeneticAlgorithmLibrary;

import GeneticAlgorithmLibrary.Chromosome.Chromosome;

/**
 * Interface for fitness functions used in genetic algorithms
 */
public interface FitnessFunction {
    /**
     * Evaluate the fitness of a chromosome
     * @param chromosome the chromosome to evaluate
     * @return fitness value (higher is better)
     */
    double evaluate(Chromosome chromosome);
}
