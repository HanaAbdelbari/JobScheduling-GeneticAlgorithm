package GeneticAlgorithmLibrary.Chromosome;

/**
 * Base interface for all chromosome types in the Genetic Algorithm library
 */
public interface Chromosome {
    /**
     * Randomly initialize the chromosome with random values
     */
    void randomInitialize();
    
    /**
     * Get the fitness value of this chromosome
     * @return fitness value
     */
    double getFitness();
    
    /**
     * Set the fitness value of this chromosome
     * @param fitness the fitness value to set
     */
    void setFitness(double fitness);
    
    /**
     * Get the length of the chromosome (number of genes)
     * @return chromosome length
     */
    int getLength();
    
    /**
     * Get a copy of this chromosome
     * @return a new chromosome with the same values
     */
    Chromosome copy();
    
    /**
     * Get string representation of the chromosome
     * @return string representation
     */
    String toString();
}
