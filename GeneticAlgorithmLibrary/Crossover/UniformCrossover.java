package GeneticAlgorithmLibrary.Crossover;

import GeneticAlgorithmLibrary.Chromosome.Chromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class UniformCrossover implements CrossoverMethod {

    private static final Random random = new Random();

    @Override
    public List<Chromosome> crossover(List<Chromosome> parents, double crossoverRate) {
        List<Chromosome> offspring = new ArrayList<>();

        for (int i = 0; i < parents.size() - 1; i += 2) {
            Chromosome parent1 = parents.get(i);
            Chromosome parent2 = parents.get(i + 1);

            if (random.nextDouble() < crossoverRate) {
                // Perform uniform crossover
                offspring.addAll(performUniformCrossover(parent1, parent2));
            } else {
                // No crossover, copy parents
                offspring.add(parent1.copy());
                offspring.add(parent2.copy());
            }
        }

        return offspring;
    }

    private List<Chromosome> performUniformCrossover(Chromosome parent1, Chromosome parent2) {
        Chromosome child1 = parent1.copy();
        Chromosome child2 = parent2.copy();

        int length = parent1.getLength();

        for (int geneIndex = 0; geneIndex < length; geneIndex++) {
            // For each gene, randomly decide which parent it comes from
            if (random.nextBoolean()) {
                // Swap genes
                Object gene1 = parent1.getGene(geneIndex);
                Object gene2 = parent2.getGene(geneIndex);

                child1.setGene(geneIndex, gene2);
                child2.setGene(geneIndex, gene1);
            }
        }

        List<Chromosome> children = new ArrayList<>();
        children.add(child1);
        children.add(child2);
        return children;
    }
}

