package GeneticAlgorithmLibrary.Mutation;

import GeneticAlgorithmLibrary.Chromosome.Chromosome;

import java.util.List;

/**
 * Interface for mutation methods in genetic algorithms
 */
public interface MutationMethod {
    /**
     * Perform mutation on chromosomes
     * @param chromosomes list of chromosomes to mutate
     * @param mutationRate probability of mutation
     */
    void mutate(List<Chromosome> chromosomes, double mutationRate);
}
