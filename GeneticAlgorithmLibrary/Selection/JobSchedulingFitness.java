package GeneticAlgorithmLibrary.Selection;

import GeneticAlgorithmLibrary.FitnessFunction;
import GeneticAlgorithmLibrary.Chromosome.*;

/**
 * Unified Fitness Function for Job Scheduling / Resource Allocation Problem
 *
 * Supports Binary, Integer, and Float Chromosomes:
 * - Binary: Selects which jobs to execute (1 = selected, 0 = not selected)
 * - Integer: Assigns each job to a machine (integer = machine index)
 * - Float: Fractional mapping of jobs to machines (continuous encoding)
 *
 * Objective:
 * Minimize the makespan = maximum machine load
 * GA maximizes fitness, so fitness = 1 / (1 + makespan)
 */
public class JobSchedulingFitness implements FitnessFunction {

    private final double[] processingTimes;  // job durations
    private final int numberOfMachines;           // number of available machines
    private final double capacity;           // optional capacity constraint
    private final double penaltyFactor = 10.0; // penalty for infeasibility

    /**
     * Constructor for unified job scheduling fitness function.
     * @param processingTimes array of job processing times
     * @param numberOfMachines number of machines
     * @param capacity optional total allowed processing time (used for binary type)
     */
    public JobSchedulingFitness(double[] processingTimes, int numberOfMachines, double capacity) {
        this.processingTimes = processingTimes;
        this.numberOfMachines = numberOfMachines;
        this.capacity = capacity;
    }

    @Override
    public double evaluate(Chromosome chromosome) {
        // Case 1: Binary Chromosome → Job selection
        if (chromosome instanceof BinaryChromosome) {
            return evaluateBinary((BinaryChromosome) chromosome);
        }

        // Case 2: Integer Chromosome → Job-to-machine assignment
        if (chromosome instanceof IntegerChromosome) {
            return evaluateInteger((IntegerChromosome) chromosome);
        }

        // Case 3: Float Chromosome → Continuous machine assignment
        if (chromosome instanceof FloatChromosome) {
            return evaluateFloat((FloatChromosome) chromosome);
        }

        // Unsupported type
        throw new IllegalArgumentException("Unsupported chromosome type for JobSchedulingFitness");
    }

    /**
     * Evaluates Binary Chromosome (Job Selection problem)
     */
    private double evaluateBinary(BinaryChromosome chromosome) {
        boolean[] genes = (boolean[]) chromosome.getGenes();
        double totalTime = 0.0;

        for (int i = 0; i < genes.length && i < processingTimes.length; i++) {
            if (genes[i]) totalTime += processingTimes[i];
        }

        // Penalize if total time exceeds capacity
        if (totalTime > capacity) {
            double penalty = penaltyFactor * (totalTime - capacity);
            return Math.max(0, capacity - penalty);
        }

        return totalTime; // higher total time = better, until capacity
    }

    /**
     * Evaluates Integer Chromosome (Classic Job Scheduling / Makespan Minimization)
     */
    private double evaluateInteger(IntegerChromosome chromosome) {
        int[] assignments = (int[]) chromosome.getGenes();
        double[] machineLoads = new double[numberOfMachines];

        // Compute total load per machine
        for (int i = 0; i < assignments.length && i < processingTimes.length; i++) {
            int machine = assignments[i] % numberOfMachines; // ensure valid index
            machineLoads[machine] += processingTimes[i];
        }

        // Find the maximum load (makespan)
        double makespan = max(machineLoads);

        // Fitness is inverse of makespan
        return 1.0 / (1.0 + makespan);
    }

    /**
     * Evaluates Float Chromosome (Continuous encoding)
     */
    private double evaluateFloat(FloatChromosome chromosome) {
        double[] genes = (double[]) chromosome.getGenes();
        double[] machineLoads = new double[numberOfMachines];

        for (int i = 0; i < genes.length && i < processingTimes.length; i++) {
            int machine = (int) Math.floor(genes[i] * numberOfMachines);
            if (machine >= numberOfMachines) machine = numberOfMachines - 1;
            machineLoads[machine] += processingTimes[i];
        }

        double makespan = max(machineLoads);
        return 1.0 / (1.0 + makespan);
    }

    /**
     * Utility: Get max from array
     */
    private double max(double[] arr) {
        double m = arr[0];
        for (double v : arr) if (v > m) m = v;
        return m;
    }
}
