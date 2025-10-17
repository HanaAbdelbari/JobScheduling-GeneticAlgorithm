package GeneticAlgorithmLibrary.Selection.Fitness;


import GeneticAlgorithmLibrary.FitnessFunction;
import GeneticAlgorithmLibrary.Chromosome.Chromosome;
import GeneticAlgorithmLibrary.Chromosome.BinaryChromosome;

public class BinaryMaxOnesFitness implements FitnessFunction {

    @Override
    public double evaluate(Chromosome chromosome) {
        if (!(chromosome instanceof BinaryChromosome)) {
            throw new IllegalArgumentException("Expected BinaryChromosome");
        }

        boolean[] genes = (boolean[]) chromosome.getGenes();
        double fitness = 0.0;

        for (boolean g : genes) {
            if (g) fitness += 1.0;
        }

        // Higher fitness = more 1’s
        return fitness;
    }
}