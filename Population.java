import java.util.ArrayList;
import java.util.List;

/**
 * Population class that manages a collection of chromosomes
 */
public class Population {
    private List<Chromosome> individuals;
    private int size;
    
    /**
     * Constructor for population
     * @param size the size of the population
     */
    public Population(int size) {
        this.size = size;
        this.individuals = new ArrayList<>();
    }
    
    /**
     * Add a chromosome to the population
     * @param chromosome the chromosome to add
     */
    public void add(Chromosome chromosome) {
        individuals.add(chromosome);
    }
    
    /**
     * Get all individuals in the population
     * @return list of chromosomes
     */
    public List<Chromosome> getIndividuals() {
        return individuals;
    }
    
    /**
     * Get the best individual (highest fitness) in the population
     * @return the best chromosome
     */
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
    
    /**
     * Get the worst individual (lowest fitness) in the population
     * @return the worst chromosome
     */
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
    
    /**
     * Get the size of the population
     * @return population size
     */
    public int getSize() {
        return individuals.size();
    }
    
    /**
     * Get the expected size of the population
     * @return expected population size
     */
    public int getExpectedSize() {
        return size;
    }
    
    /**
     * Check if the population is full
     * @return true if population is full
     */
    public boolean isFull() {
        return individuals.size() >= size;
    }
    
    /**
     * Clear all individuals from the population
     */
    public void clear() {
        individuals.clear();
    }
    
    /**
     * Replace the entire population with new individuals
     * @param newIndividuals the new individuals to replace with
     */
    public void replace(List<Chromosome> newIndividuals) {
        individuals.clear();
        individuals.addAll(newIndividuals);
    }
    
    /**
     * Get average fitness of the population
     * @return average fitness
     */
    public double getAverageFitness() {
        if (individuals.isEmpty()) {
            return 0.0;
        }
        
        double sum = 0.0;
        for (Chromosome individual : individuals) {
            sum += individual.getFitness();
        }
        return sum / individuals.size();
    }
    
    /**
     * Get string representation of the population
     * @return string representation
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Population (Size: ").append(individuals.size()).append("):\n");
        for (int i = 0; i < individuals.size(); i++) {
            sb.append("  ").append(i + 1).append(": ").append(individuals.get(i).toString())
              .append(" (Fitness: ").append(String.format("%.4f", individuals.get(i).getFitness())).append(")\n");
        }
        return sb.toString();
    }
}
