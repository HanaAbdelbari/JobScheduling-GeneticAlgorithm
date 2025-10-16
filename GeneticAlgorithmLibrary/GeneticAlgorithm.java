package GeneticAlgorithmLibrary;

import GeneticAlgorithmLibrary.Chromosome.Chromosome;
import GeneticAlgorithmLibrary.Crossover.CrossoverMethod;
import GeneticAlgorithmLibrary.Mutation.MutationMethod;
import GeneticAlgorithmLibrary.Replacement.ReplacementStrategy;
import GeneticAlgorithmLibrary.Selection.SelectionMethod;

import java.util.List;

/**
 * Main Genetic Algorithm class that implements the core GA engine
 * This class manages the overall algorithm flow and population initialization
 */
public class GeneticAlgorithm {
    // Core GA parameters
    private int populationSize;
    private int generations;
    private double crossoverRate;
    private double mutationRate;
    
    // GA components (interfaces for modularity)
    private SelectionMethod selectionMethod;
    private CrossoverMethod crossoverMethod;
    private MutationMethod mutationMethod;
    private ReplacementStrategy replacementStrategy;
    
    // Problem-specific components
    private FitnessFunction fitnessFunction;
    private InfeasibilityHandler infeasibilityHandler;
    
    // GeneticAlgorithmLibrary.Chromosome.GeneticAlgorithmLibrary.Chromosome prototype for initialization
    private Chromosome chromosomePrototype;
    
    // Current population
    private Population population;
    
    /**
     * Constructor for GeneticAlgorithmLibrary.GeneticAlgorithm
     * @param populationSize size of the population
     * @param generations number of generations to run
     * @param crossoverRate probability of crossover
     * @param mutationRate probability of mutation
     * @param chromosomePrototype prototype chromosome for initialization
     * @param fitnessFunction fitness function to evaluate chromosomes
     */
    public GeneticAlgorithm(int populationSize, int generations, double crossoverRate, 
                          double mutationRate, Chromosome chromosomePrototype, 
                          FitnessFunction fitnessFunction) {
        this.populationSize = populationSize;
        this.generations = generations;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.chromosomePrototype = chromosomePrototype;
        this.fitnessFunction = fitnessFunction;
        
        // Initialize with default components (will be replaced later)
        this.selectionMethod = null;
        this.crossoverMethod = null;
        this.mutationMethod = null;
        this.replacementStrategy = null;
        this.infeasibilityHandler = null;
    }
    
    /**
     * Set the selection method
     * @param selectionMethod the selection method to use
     */
    public void setSelectionMethod(SelectionMethod selectionMethod) {
        this.selectionMethod = selectionMethod;
    }
    
    /**
     * Set the crossover method
     * @param crossoverMethod the crossover method to use
     */
    public void setCrossoverMethod(CrossoverMethod crossoverMethod) {
        this.crossoverMethod = crossoverMethod;
    }
    
    /**
     * Set the mutation method
     * @param mutationMethod the mutation method to use
     */
    public void setMutationMethod(MutationMethod mutationMethod) {
        this.mutationMethod = mutationMethod;
    }
    
    /**
     * Set the replacement strategy
     * @param replacementStrategy the replacement strategy to use
     */
    public void setReplacementStrategy(ReplacementStrategy replacementStrategy) {
        this.replacementStrategy = replacementStrategy;
    }
    
    /**
     * Set the infeasibility handler
     * @param infeasibilityHandler the infeasibility handler to use
     */
    public void setInfeasibilityHandler(InfeasibilityHandler infeasibilityHandler) {
        this.infeasibilityHandler = infeasibilityHandler;
    }
    
    /**
     * Initialize the population with random chromosomes
     */
    private void initializePopulation() {
        population = new Population(populationSize);
        
        for (int i = 0; i < populationSize; i++) {
            Chromosome individual = chromosomePrototype.copy();
            individual.randomInitialize();
            population.add(individual);
        }
    }
    
    /**
     * Evaluate the fitness of all individuals in the population
     * @param population the population to evaluate
     */
    private void evaluatePopulation(Population population) {
        for (Chromosome chromosome : population.getIndividuals()) {
            double fitness = fitnessFunction.evaluate(chromosome);
            chromosome.setFitness(fitness);
        }
    }
    
    /**
     * Run the genetic algorithm
     * @return the best chromosome found
     */
    public Chromosome run() {
        // Check if all required components are set
        if (selectionMethod == null || crossoverMethod == null || 
            mutationMethod == null || replacementStrategy == null) {
            throw new IllegalStateException("All GA components must be set before running the algorithm");
        }
        
        // Initialize population
        initializePopulation();
        evaluatePopulation(population);
        
        Chromosome best = population.getBestIndividual();
        System.out.println("Initial Best fitness: " + String.format("%.4f", best.getFitness()));
        
        // Main GA loop
        for (int gen = 0; gen < generations; gen++) {
            // GeneticAlgorithmLibrary.Selection: Choose parents
            List<Chromosome> parents = selectionMethod.select(population);
            
            // GeneticAlgorithmLibrary.Crossover: Generate offspring
            List<Chromosome> offspring = crossoverMethod.crossover(parents, crossoverRate);
            
            // GeneticAlgorithmLibrary.Mutation: Apply mutations
            mutationMethod.mutate(offspring, mutationRate);
            
            // Handle infeasible solutions
            if (infeasibilityHandler != null) {
                for (Chromosome child : offspring) {
                    if (infeasibilityHandler.isInfeasible(child)) {
                        infeasibilityHandler.repair(child);
                    }
                }
            }
            
            // GeneticAlgorithmLibrary.Replacement: Update population
            population = replacementStrategy.replace(population, offspring);
            
            // Evaluation: Calculate fitness for new population
            evaluatePopulation(population);
            
            // Track best solution
            Chromosome currentBest = population.getBestIndividual();
            if (currentBest.getFitness() > best.getFitness()) {
                best = currentBest;
            }
            
            // Print progress
            System.out.println("Generation " + gen + " → Best fitness: " + 
                             String.format("%.4f", best.getFitness()));
        }
        
        return best;
    }
    
    /**
     * Get the current population
     * @return the current population
     */
    public Population getPopulation() {
        return population;
    }
    
    /**
     * Get population size
     * @return population size
     */
    public int getPopulationSize() {
        return populationSize;
    }
    
    /**
     * Get number of generations
     * @return number of generations
     */
    public int getGenerations() {
        return generations;
    }
    
    /**
     * Get crossover rate
     * @return crossover rate
     */
    public double getCrossoverRate() {
        return crossoverRate;
    }
    
    /**
     * Get mutation rate
     * @return mutation rate
     */
    public double getMutationRate() {
        return mutationRate;
    }
}
