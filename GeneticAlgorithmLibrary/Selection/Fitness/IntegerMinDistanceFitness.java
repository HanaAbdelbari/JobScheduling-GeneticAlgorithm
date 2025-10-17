package GeneticAlgorithmLibrary.Selection.Fitness;

import GeneticAlgorithmLibrary.FitnessFunction;
import GeneticAlgorithmLibrary.Chromosome.Chromosome;
import GeneticAlgorithmLibrary.Chromosome.IntegerChromosome;

public class IntegerMinDistanceFitness implements FitnessFunction {

    @Override
    public double evaluate(Chromosome chromosome) {
        if (!(chromosome instanceof IntegerChromosome)) {
            throw new IllegalArgumentException("Expected IntegerChromosome");
        }

        int[] genes = (int[]) chromosome.getGenes();
        double totalDistance = 0.0;

        for (int i = 0; i < genes.length - 1; i++) {
            totalDistance += Math.abs(genes[i + 1] - genes[i]);
        }

        // Lower distance = better fitness, but GA maximizes, so invert
        return 1.0 / (1.0 + totalDistance);
    }
}