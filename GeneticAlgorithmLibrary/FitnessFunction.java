package GeneticAlgorithmLibrary;

import GeneticAlgorithmLibrary.Chromosome.Chromosome;


public interface FitnessFunction {

    double evaluate(Chromosome chromosome);
}
