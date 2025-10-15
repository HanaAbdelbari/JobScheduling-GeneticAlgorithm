/**
 * Demo class to test the GA Core Engine
 * This demonstrates the Overall Algorithm & Population Initialization functionality
 */
public class GADemo {
    
    public static void main(String[] args) {
        System.out.println("=== GA Core Engine Demo ===");
        System.out.println("Testing Overall Algorithm & Population Initialization\n");
        
        // Test with Binary Chromosomes
        testBinaryChromosomes();
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Test with Integer Chromosomes
        testIntegerChromosomes();
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Test with Float Chromosomes
        testFloatChromosomes();
    }
    
    /**
     * Test GA with binary chromosomes
     */
    private static void testBinaryChromosomes() {
        System.out.println("Testing Binary Chromosomes:");
        System.out.println("Objective: Maximize sum of genes (all 1s = best solution)\n");
        
        // Create chromosome prototype
        BinaryChromosome prototype = new BinaryChromosome(10);
        
        // Create fitness function
        SumFitnessFunction fitnessFunction = new SumFitnessFunction();
        
        // Create GA with parameters
        GeneticAlgorithm ga = new GeneticAlgorithm(
            20,    // population size
            10,    // generations
            0.8,   // crossover rate
            0.1,   // mutation rate
            prototype,
            fitnessFunction
        );
        
        // Set GA components
        ga.setSelectionMethod(new RandomSelection(20));
        ga.setCrossoverMethod(new SinglePointCrossover());
        ga.setMutationMethod(new RandomMutation());
        ga.setReplacementStrategy(new GenerationalReplacement());
        
        // Run GA
        Chromosome best = ga.run();
        
        System.out.println("\nFinal Best Solution: " + best.toString());
        System.out.println("Final Fitness: " + String.format("%.4f", best.getFitness()));
        System.out.println("Expected Best: [1,1,1,1,1,1,1,1,1,1] with fitness 10.0");
    }
    
    /**
     * Test GA with integer chromosomes
     */
    private static void testIntegerChromosomes() {
        System.out.println("Testing Integer Chromosomes:");
        System.out.println("Objective: Maximize sum of genes (all max values = best solution)\n");
        
        // Create chromosome prototype
        IntegerChromosome prototype = new IntegerChromosome(8, 0, 5);
        
        // Create fitness function
        SumFitnessFunction fitnessFunction = new SumFitnessFunction();
        
        // Create GA with parameters
        GeneticAlgorithm ga = new GeneticAlgorithm(
            15,    // population size
            8,     // generations
            0.7,   // crossover rate
            0.15,  // mutation rate
            prototype,
            fitnessFunction
        );
        
        // Set GA components
        ga.setSelectionMethod(new RandomSelection(15));
        ga.setCrossoverMethod(new SinglePointCrossover());
        ga.setMutationMethod(new RandomMutation());
        ga.setReplacementStrategy(new GenerationalReplacement());
        
        // Run GA
        Chromosome best = ga.run();
        
        System.out.println("\nFinal Best Solution: " + best.toString());
        System.out.println("Final Fitness: " + String.format("%.4f", best.getFitness()));
        System.out.println("Expected Best: [5,5,5,5,5,5,5,5] with fitness 40.0");
    }
    
    /**
     * Test GA with float chromosomes
     */
    private static void testFloatChromosomes() {
        System.out.println("Testing Float Chromosomes:");
        System.out.println("Objective: Maximize sum of genes (all max values = best solution)\n");
        
        // Create chromosome prototype
        FloatChromosome prototype = new FloatChromosome(6, 0.0, 10.0);
        
        // Create fitness function
        SumFitnessFunction fitnessFunction = new SumFitnessFunction();
        
        // Create GA with parameters
        GeneticAlgorithm ga = new GeneticAlgorithm(
            12,    // population size
            6,     // generations
            0.9,   // crossover rate
            0.2,   // mutation rate
            prototype,
            fitnessFunction
        );
        
        // Set GA components
        ga.setSelectionMethod(new RandomSelection(12));
        ga.setCrossoverMethod(new SinglePointCrossover());
        ga.setMutationMethod(new RandomMutation());
        ga.setReplacementStrategy(new GenerationalReplacement());
        
        // Run GA
        Chromosome best = ga.run();
        
        System.out.println("\nFinal Best Solution: " + best.toString());
        System.out.println("Final Fitness: " + String.format("%.4f", best.getFitness()));
        System.out.println("Expected Best: [10.0,10.0,10.0,10.0,10.0,10.0] with fitness ~60.0");
    }
}
