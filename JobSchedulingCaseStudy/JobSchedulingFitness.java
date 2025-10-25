package JobSchedulingCaseStudy;

import GeneticAlgorithmLibrary.FitnessFunction;
import GeneticAlgorithmLibrary.Chromosome.*;


public class JobSchedulingFitness implements FitnessFunction {

    private final double[] processingTimes;
    private final int numberOfMachines;
    private final double capacity;

    public JobSchedulingFitness(double[] processingTimes, int numberOfMachines, double capacity) {
        this.processingTimes = processingTimes;
        this.numberOfMachines = numberOfMachines;
        this.capacity = capacity;
    }

    public double[] getProcessingTimes() { return processingTimes; }
    public int getNumberOfMachines() { return numberOfMachines; }
    public double getCapacity() { return capacity; }

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

    private double evaluateBinary(BinaryChromosome chromosome) {
        boolean[] genes = (boolean[]) chromosome.getGenes();
        double totalTime = 0.0;

        for (int i = 0; i < genes.length && i < processingTimes.length; i++) {
            if (genes[i]) totalTime += processingTimes[i];
        }

        if (totalTime > capacity) {
            return 1e-9;
        }

        return Math.min(totalTime / capacity, 1.0);
    }


    private double evaluateInteger(IntegerChromosome chromosome) {
        int[] assignments = (int[]) chromosome.getGenes();
        double[] machineLoads = new double[numberOfMachines];

        // Calculate load per machine
        for (int i = 0; i < assignments.length && i < processingTimes.length; i++) {
            int machine = Math.abs(assignments[i]) % numberOfMachines;
            machineLoads[machine] += processingTimes[i];
        }

        double penalty = 0.0;
        for (double load : machineLoads) {
            if (load > capacity) {
                penalty += (load - capacity); // overload penalty
            }
        }

        double makespan = max(machineLoads);
        double avgUtilization = averageUtilization(machineLoads);

        return avgUtilization / (1.0 + makespan + 17 * penalty);
    }

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

        double penalty = 0.0;
        for (double load : machineLoads) {
            if (load > capacity) {
                penalty += (load - capacity); // overload penalty
            }
        }

        double makespan = max(machineLoads);
        double avgUtilization = averageUtilization(machineLoads);

        return avgUtilization / (1.0 + makespan + 17 * penalty);
    }


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
