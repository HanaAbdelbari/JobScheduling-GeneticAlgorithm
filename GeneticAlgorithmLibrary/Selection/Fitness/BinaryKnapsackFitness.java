package GeneticAlgorithmLibrary.Selection.Fitness;

import GeneticAlgorithmLibrary.FitnessFunction;
import GeneticAlgorithmLibrary.Chromosome.BinaryChromosome;
import GeneticAlgorithmLibrary.Chromosome.Chromosome;
import java.util.List;

public class BinaryKnapsackFitness implements FitnessFunction {
    private final List<Double> values;
    private final List<Double> weights;
    private final double capacity;
    private final double penaltyFactor;

    public BinaryKnapsackFitness(List<Double> values, List<Double> weights, double capacity) {
        if (values.size() != weights.size()) {
            throw new IllegalArgumentException("Values and weights must have same length");
        }
        this.values = values;
        this.weights = weights;
        this.capacity = capacity;
        this.penaltyFactor = 10.0; // used to penalize infeasible (overweight) solutions
    }

    @Override
    public double evaluate(Chromosome chromosome) {
        if (!(chromosome instanceof BinaryChromosome)) {
            throw new IllegalArgumentException("Expected BinaryChromosome");
        }

        boolean[] genes = (boolean[]) chromosome.getGenes();
        double totalValue = 0;
        double totalWeight = 0;

        for (int i = 0; i < genes.length; i++) {
            if (genes[i]) {
                totalValue += values.get(i);
                totalWeight += weights.get(i);
            }
        }

        if (totalWeight > capacity) {
            double penalty = penaltyFactor * (totalWeight - capacity);
            return Math.max(0, totalValue - penalty);
        }
        return totalValue;
    }
}