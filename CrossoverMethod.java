import java.util.List;

/**
 * Interface for crossover methods in genetic algorithms
 */
public interface CrossoverMethod {
    /**
     * Perform crossover on parent chromosomes
     * @param parents list of parent chromosomes
     * @param crossoverRate probability of crossover
     * @return list of offspring chromosomes
     */
    List<Chromosome> crossover(List<Chromosome> parents, double crossoverRate);
}
