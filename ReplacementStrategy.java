import java.util.List;

/**
 * Interface for replacement strategies in genetic algorithms
 */
public interface ReplacementStrategy {
    /**
     * Replace individuals in the population with offspring
     * @param currentPopulation the current population
     * @param offspring the new offspring to integrate
     * @return the new population after replacement
     */
    Population replace(Population currentPopulation, List<Chromosome> offspring);
}
