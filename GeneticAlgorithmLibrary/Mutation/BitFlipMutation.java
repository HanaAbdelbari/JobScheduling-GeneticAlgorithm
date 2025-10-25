package GeneticAlgorithmLibrary.Mutation;

import GeneticAlgorithmLibrary.Chromosome.BinaryChromosome;
import GeneticAlgorithmLibrary.Chromosome.Chromosome;

import java.util.List;


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


