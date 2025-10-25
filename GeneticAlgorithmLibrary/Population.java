package GeneticAlgorithmLibrary;

import GeneticAlgorithmLibrary.Chromosome.Chromosome;

import java.util.ArrayList;
import java.util.List;


public class Population {
    private List<Chromosome> individuals;
    private int size;


    public Population(int size) {
        this.size = size;
        this.individuals = new ArrayList<>();
    }


    public void add(Chromosome chromosome) {
        individuals.add(chromosome);
    }


    public List<Chromosome> getIndividuals() {
        return individuals;
    }

    public void setIndividuals(List<Chromosome> individuals) {
        this.individuals = individuals;
    }


    public Chromosome getBestIndividual() {
        if (individuals.isEmpty()) {
            return null;
        }

        Chromosome best = individuals.get(0);
        for (Chromosome individual : individuals) {
            if (individual.getFitness() > best.getFitness()) {
                best = individual;
            }
        }
        return best;
    }


    public Chromosome getWorstIndividual() {
        if (individuals.isEmpty()) {
            return null;
        }

        Chromosome worst = individuals.get(0);
        for (Chromosome individual : individuals) {
            if (individual.getFitness() < worst.getFitness()) {
                worst = individual;
            }
        }
        return worst;
    }

    public int getSize() {
        return individuals.size();
    }


    public void replace(List<Chromosome> newIndividuals) {
        individuals.clear();
        individuals.addAll(newIndividuals);
    }

}
