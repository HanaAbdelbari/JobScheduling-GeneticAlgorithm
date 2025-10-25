package GeneticAlgorithmLibrary.Replacement;

import GeneticAlgorithmLibrary.Population;
import GeneticAlgorithmLibrary.Chromosome.Chromosome;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class ElitistReplacement implements ReplacementMethod {

    private final int numElites;

    public ElitistReplacement(int numElites) {
        this.numElites = numElites;
    }

    @Override
    public Population replace(Population oldPopulation, Population newPopulation) {
        List<Chromosome> oldGen = oldPopulation.getIndividuals();
        List<Chromosome> newGen = newPopulation.getIndividuals();
        List<Chromosome> nextGen = new ArrayList<>();

        // Keep top N best from the current generation
        oldGen.sort(Comparator.comparingDouble(Chromosome::getFitness).reversed());
        for (int i = 0; i < Math.min(numElites, oldGen.size()); i++) {
            nextGen.add(oldGen.get(i).clone());
        }

        // Fill the rest with offspring until reaching population size
        for (int i = 0; nextGen.size() < oldGen.size() && i < newGen.size(); i++) {
            nextGen.add(newGen.get(i).clone());
        }

        oldPopulation.setIndividuals(nextGen);
        return oldPopulation;
    }
}
