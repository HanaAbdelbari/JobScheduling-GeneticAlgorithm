package GeneticAlgorithmLibrary;

import GeneticAlgorithmLibrary.Chromosome.Chromosome;

/**
 * Interface for handling infeasible solutions in genetic algorithms
 */
public interface InfeasibilityHandler {
    /**
     * Check if a chromosome represents an infeasible solution
     * @param chromosome the chromosome to check
     * @return true if the solution is infeasible
     */
    boolean isInfeasible(Chromosome chromosome);
    
    /**
     * Repair an infeasible chromosome
     * @param chromosome the chromosome to repair
     */
    void repair(Chromosome chromosome);
}
