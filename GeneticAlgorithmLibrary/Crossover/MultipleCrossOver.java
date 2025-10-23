package GeneticAlgorithmLibrary.Crossover;

import GeneticAlgorithmLibrary.Chromosome.Chromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class TwoPointCrossover implements CrossoverMethod {

    private static final Random random = new Random();

    @Override
    public List<Chromosome> crossover(List<Chromosome> parents, double crossoverRate) {
        List<Chromosome> offspring = new ArrayList<>();

        for (int i = 0; i < parents.size() - 1; i += 2) {
            Chromosome parent1 = parents.get(i);
            Chromosome parent2 = parents.get(i + 1);

            if (random.nextDouble() < crossoverRate) {
                offspring.addAll(performTwoPointCrossover(parent1, parent2));
            } else {
                offspring.add(parent1.copy());
                offspring.add(parent2.copy());
            }
        }

        return offspring;
    }

    private List<Chromosome> performTwoPointCrossover(Chromosome parent1, Chromosome parent2) {
        int length = parent1.getLength();

        // Generate two distinct random crossover points
        int point1 = random.nextInt(length);
        int point2 = random.nextInt(length);

        // Ensure point1 < point2
        if (point1 > point2) {
            int temp = point1;
            point1 = point2;
            point2 = temp;
        }

        Chromosome child1 = parent1.copy();
        Chromosome child2 = parent2.copy();

        // Swap the genes between the two points
        for (int i = point1; i <= point2; i++) {
            Object gene1 = parent1.getGene(i);
            Object gene2 = parent2.getGene(i);

            child1.setGene(i, gene2);
            child2.setGene(i, gene1);
        }

        List<Chromosome> children = new ArrayList<>();
        children.add(child1);
        children.add(child2);
        return children;
    }
}

