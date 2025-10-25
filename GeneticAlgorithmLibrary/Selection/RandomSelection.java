package GeneticAlgorithmLibrary.Selection;

import GeneticAlgorithmLibrary.Chromosome.Chromosome;
import GeneticAlgorithmLibrary.Population;

import java.util.ArrayList;
import java.util.List;


public class RandomSelection implements SelectionMethod {
    private int numParents;


    public RandomSelection(int numParents) {
        this.numParents = numParents;
    }

    @Override
    public List<Chromosome> select(Population population, int numParents) {
        this.numParents = numParents;
        return select(population);
    }


    @Override
    public List<Chromosome> select(Population population) {
        List<Chromosome> parents = new ArrayList<>();
        List<Chromosome> individuals = population.getIndividuals();

        for (int i = 0; i < numParents; i++) {
            int randomIndex = (int)(Math.random() * individuals.size());
            parents.add(individuals.get(randomIndex));
        }

        return parents;
    }
}