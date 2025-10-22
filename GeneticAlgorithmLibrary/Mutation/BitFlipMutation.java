package GeneticAlgorithmLibrary.Mutation;

import GeneticAlgorithmLibrary.Chromosome.BinaryChromosome;
import GeneticAlgorithmLibrary.Chromosome.Chromosome;

import java.util.List;

/**
 * Bit-flip mutation for binary chromosomes.
 * Picks a random bit and flips it (0 <-> 1) with probability = mutationRate per chromosome.
 */
public class BitFlipMutation implements MutationMethod {

    @Override
    public void mutate(List<Chromosome> chromosomes, double mutationRate) {
        for (Chromosome chromosome : chromosomes) {
            if (chromosome instanceof BinaryChromosome) {
                BinaryChromosome binary = (BinaryChromosome) chromosome;
                boolean[] genes = (boolean[]) binary.getGenes();
                for (int i = 0; i < binary.getLength(); i++) {
                    if (Math.random() < mutationRate) {
                        genes[i] = !genes[i];
                    }
                }
                binary.setGenes(genes);
            }
        }
    }
}


