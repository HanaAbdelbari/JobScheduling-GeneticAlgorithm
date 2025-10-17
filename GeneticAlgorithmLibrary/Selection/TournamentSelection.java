package GeneticAlgorithmLibrary.Selection;

import GeneticAlgorithmLibrary.Population;
import GeneticAlgorithmLibrary.Chromosome.Chromosome;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Tournament Selection:
 * Randomly selects k individuals from the population and chooses the best among them.
 */
public class TournamentSelection implements SelectionMethod {
    private final int tournamentSize;
    private final Random random = new Random();

    public TournamentSelection(int tournamentSize) {
        if (tournamentSize < 2)
            throw new IllegalArgumentException("Tournament size must be at least 2");
        this.tournamentSize = tournamentSize;
    }

    /** Selection with specified number of parents */
    @Override
    public List<Chromosome> select(Population population, int numParents) {
        List<Chromosome> selected = new ArrayList<>();
        List<Chromosome> individuals = population.getIndividuals();

        for (int i = 0; i < numParents; i++) {
            Chromosome best = null;
            for (int j = 0; j < tournamentSize; j++) {
                Chromosome candidate = individuals.get(random.nextInt(individuals.size()));
                if (best == null || candidate.getFitness() > best.getFitness()) {
                    best = candidate;
                }
            }
            selected.add(best.clone());
        }
        return selected;
    }

    /** Default selection — selects two parents */
    @Override
    public List<Chromosome> select(Population population) {
        return select(population, 2);  // default to selecting 2 parents
    }
}
