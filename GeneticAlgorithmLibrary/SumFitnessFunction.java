package GeneticAlgorithmLibrary;

import GeneticAlgorithmLibrary.Chromosome.BinaryChromosome;
import GeneticAlgorithmLibrary.Chromosome.Chromosome;
import GeneticAlgorithmLibrary.Chromosome.FloatChromosome;
import GeneticAlgorithmLibrary.Chromosome.IntegerChromosome;

/**
 * Sample fitness function that maximizes the sum of genes
 * This is a simple example for demonstration purposes
 */
public class SumFitnessFunction implements FitnessFunction {
    
    @Override
    public double evaluate(Chromosome chromosome) {
        double sum = 0.0;
        
        // Handle different chromosome types
        if (chromosome instanceof BinaryChromosome) {
            BinaryChromosome binary = (BinaryChromosome) chromosome;
            for (int i = 0; i < binary.getLength(); i++) {
                sum += binary.getGene(i);
            }
        } else if (chromosome instanceof IntegerChromosome) {
            IntegerChromosome integer = (IntegerChromosome) chromosome;
            for (int i = 0; i < integer.getLength(); i++) {
                sum += integer.getGene(i);
            }
        } else if (chromosome instanceof FloatChromosome) {
            FloatChromosome floatChromosome = (FloatChromosome) chromosome;
            for (int i = 0; i < floatChromosome.getLength(); i++) {
                sum += floatChromosome.getGene(i);
            }
        }
        
        return sum;
    }
}
