package GeneticAlgorithmLibrary.Selection;

import GeneticAlgorithmLibrary.Population;
import GeneticAlgorithmLibrary.Chromosome.Chromosome;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Roulette Wheel Selection:
 * Selects individuals based on fitness proportion — higher fitness = higher chance.
 */
public class RouletteWheelSelection implements SelectionMethod {
    private final Random random = new Random();

    /** Selection with specified number of parents */
    @Override
    public List<Chromosome> select(Population population, int numParents) {
        List<Chromosome> selected = new ArrayList<>();
        List<Chromosome> individuals = population.getIndividuals();

        double totalFitness = 0;
        for (Chromosome c : individuals) {
            totalFitness += c.getFitness();
        }

        // Handle case of all-zero fitness (prevent division by zero)
        if (totalFitness == 0) {
            for (int i = 0; i < numParents; i++) {
                selected.add(individuals.get(random.nextInt(individuals.size())).clone());
            }
            return selected;
        }

        for (int i = 0; i < numParents; i++) {
            double rand = random.nextDouble() * totalFitness;
            double runningSum = 0;
            for (Chromosome c : individuals) {
                runningSum += c.getFitness();
                if (runningSum >= rand) {
                    selected.add(c.clone());
                    break;
                }
            }
        }
        return selected;
    }

    /** Default selection — selects two parents */
    @Override
    public List<Chromosome> select(Population population) {
        return select(population, 2);
    }
}
