package GeneticAlgorithmLibrary.Selection.Fitness;

import GeneticAlgorithmLibrary.FitnessFunction;
import GeneticAlgorithmLibrary.Chromosome.Chromosome;
import GeneticAlgorithmLibrary.Chromosome.FloatChromosome;

public class FloatFunctionFitness implements FitnessFunction {

    @Override
    public double evaluate(Chromosome chromosome) {
        if (!(chromosome instanceof FloatChromosome)) {
            throw new IllegalArgumentException("Expected FloatChromosome");
        }

        double[] genes = (double[]) chromosome.getGenes();
        double sum = 0.0;

        for (double x : genes) {
            // Example function: f(x) = sin(x) * x
            sum += Math.sin(x) * x;
        }

        // Maximize total f(x)
        return sum;
    }
}