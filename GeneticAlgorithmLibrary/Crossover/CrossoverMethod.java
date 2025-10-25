package GeneticAlgorithmLibrary.Crossover;

import GeneticAlgorithmLibrary.Chromosome.Chromosome;

import java.util.List;


public interface CrossoverMethod {

    List<Chromosome> crossover(List<Chromosome> parents, double crossoverRate);
}
