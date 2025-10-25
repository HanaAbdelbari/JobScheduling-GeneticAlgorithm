package GeneticAlgorithmLibrary.Replacement;

import GeneticAlgorithmLibrary.Population;
import GeneticAlgorithmLibrary.Chromosome.Chromosome;

import java.util.Comparator;
import java.util.List;
import java.util.Random;


public class SteadyStateReplacement implements ReplacementMethod {

    private final int numToReplace;
    private final Random random = new Random();

    public SteadyStateReplacement(int numToReplace) {
        this.numToReplace = numToReplace;
    }

    @Override
    public Population replace(Population oldPopulation, Population newPopulation) {
        List<Chromosome> current = oldPopulation.getIndividuals();
        List<Chromosome> offspring = newPopulation.getIndividuals();

        // Sort weakest to strongest
        current.sort(Comparator.comparingDouble(Chromosome::getFitness));

        // Replace K weakest with random offspring
        for (int i = 0; i < Math.min(numToReplace, offspring.size()); i++) {
            Chromosome child = offspring.get(random.nextInt(offspring.size())).clone();
            current.set(i, child);
        }

        oldPopulation.setIndividuals(current);
        return oldPopulation;
    }
}
