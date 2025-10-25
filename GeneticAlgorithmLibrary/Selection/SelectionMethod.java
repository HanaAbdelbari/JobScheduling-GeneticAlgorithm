package GeneticAlgorithmLibrary.Selection;

import GeneticAlgorithmLibrary.Chromosome.Chromosome;
import GeneticAlgorithmLibrary.Population;

import java.util.List;


public interface SelectionMethod {
    List<Chromosome> select(Population population, int numParents);

    List<Chromosome> select(Population population);
}