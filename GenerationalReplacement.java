import java.util.List;

/**
 * Simple generational replacement strategy for demonstration
 * This replaces the entire population with offspring
 */
public class GenerationalReplacement implements ReplacementStrategy {
    
    @Override
    public Population replace(Population currentPopulation, List<Chromosome> offspring) {
        Population newPopulation = new Population(currentPopulation.getExpectedSize());
        
        // Add all offspring to new population
        for (Chromosome offspring : offspring) {
            newPopulation.add(offspring);
        }
        
        return newPopulation;
    }
}
