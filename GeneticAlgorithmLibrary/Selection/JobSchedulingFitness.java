package GeneticAlgorithmLibrary.Selection;

import GeneticAlgorithmLibrary.FitnessFunction;
import GeneticAlgorithmLibrary.Chromosome.*;

/**
 * Unified Fitness Function for Job Scheduling / Resource Allocation Problem
 * Objective:
 * - Use all available machines efficiently (maximize utilization)
 * - Avoid overloading any machine (capacity constraint)
 * - Minimize makespan (total working time)
 * Works with Binary, Integer, and Float chromosomes.
 */
public class JobSchedulingFitness implements FitnessFunction {

    private final double[] processingTimes;   // Job durations
    private final int numberOfMachines;       // Number of machines/resources
    private final double capacity;            // Per-machine max capacity
    private final double penaltyFactor = 10.0; // Penalty for over-capacity

    public JobSchedulingFitness(double[] processingTimes, int numberOfMachines, double capacity) {
        this.processingTimes = processingTimes;
        this.numberOfMachines = numberOfMachines;
        this.capacity = capacity;
    }

    @Override
    public double evaluate(Chromosome chromosome) {
        if (chromosome instanceof BinaryChromosome)
            return evaluateBinary((BinaryChromosome) chromosome);
        if (chromosome instanceof IntegerChromosome)
            return evaluateInteger((IntegerChromosome) chromosome);
        if (chromosome instanceof FloatChromosome)
            return evaluateFloat((FloatChromosome) chromosome);

        throw new IllegalArgumentException("Unsupported chromosome type for JobSchedulingFitness");
    }

    // -------------------------------------------------------------------------
    // 1️⃣ BINARY: Job Selection Under Total Capacity
    // -------------------------------------------------------------------------
    private double evaluateBinary(BinaryChromosome chromosome) {
        boolean[] genes = (boolean[]) chromosome.getGenes();
        double totalTime = 0.0;

        // Sum selected jobs
        for (int i = 0; i < genes.length && i < processingTimes.length; i++) {
            if (genes[i]) totalTime += processingTimes[i];
        }

        // Penalize over-capacity
        double penalty = 0.0;
        if (totalTime > capacity)
            penalty = penaltyFactor * (totalTime - capacity);

        // Utilization = fraction of used capacity (clamped to [0, 1])
        double utilization = Math.min(totalTime / capacity, 1.0);

        // Fitness encourages high utilization but punishes overload
        return utilization / (1.0 + penalty);
    }

    // -------------------------------------------------------------------------
    // 2️⃣ INTEGER: Discrete Job-to-Machine Assignment
    // -------------------------------------------------------------------------
    private double evaluateInteger(IntegerChromosome chromosome) {
        int[] assignments = (int[]) chromosome.getGenes();
        double[] machineLoads = new double[numberOfMachines];

        // Calculate load per machine
        for (int i = 0; i < assignments.length && i < processingTimes.length; i++) {
            int machine = Math.abs(assignments[i]) % numberOfMachines;
            machineLoads[machine] += processingTimes[i];
        }

        // Calculate penalties + metrics
        double makespan = max(machineLoads);
        double penalty = overCapacityPenalty(machineLoads);
        double avgUtilization = averageUtilization(machineLoads);

        // Combine objectives: high utilization, low makespan, low penalty
        return avgUtilization / (1.0 + makespan + penaltyFactor * penalty);
    }

    // -------------------------------------------------------------------------
    // 3️⃣ FLOAT: Continuous Job-to-Machine Assignment
    // -------------------------------------------------------------------------
    private double evaluateFloat(FloatChromosome chromosome) {
        double[] genes = (double[]) chromosome.getGenes();
        double[] machineLoads = new double[numberOfMachines];

        // Continuous assignment to nearest machine
        for (int i = 0; i < genes.length && i < processingTimes.length; i++) {
            int machine = (int) Math.floor(genes[i] * numberOfMachines);
            if (machine >= numberOfMachines) machine = numberOfMachines - 1;
            if (machine < 0) machine = 0;
            machineLoads[machine] += processingTimes[i];
        }

        double makespan = max(machineLoads);
        double penalty = overCapacityPenalty(machineLoads);
        double avgUtilization = averageUtilization(machineLoads);

        return avgUtilization / (1.0 + makespan + penaltyFactor * penalty);
    }

    // -------------------------------------------------------------------------
    // Utility Methods
    // -------------------------------------------------------------------------
    private double max(double[] arr) {
        double maxVal = arr[0];
        for (double v : arr) if (v > maxVal) maxVal = v;
        return maxVal;
    }

    private double overCapacityPenalty(double[] loads) {
        double penalty = 0.0;
        for (double load : loads)
            if (load > capacity) penalty += (load - capacity);
        return penalty;
    }

    private double averageUtilization(double[] loads) {
        double total = 0.0;
        for (double load : loads)
            total += Math.min(load, capacity) / capacity;
        return total / numberOfMachines;
    }
}
