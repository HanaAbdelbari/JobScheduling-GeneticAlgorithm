package GeneticAlgorithmLibrary.Mutation;

import GeneticAlgorithmLibrary.Chromosome.Chromosome;

import java.util.List;

public interface MutationMethod {

    void mutate(List<Chromosome> chromosomes, double mutationRate);
}
