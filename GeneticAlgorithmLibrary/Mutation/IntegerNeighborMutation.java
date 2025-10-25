package GeneticAlgorithmLibrary.Mutation;

import GeneticAlgorithmLibrary.Chromosome.Chromosome;
import GeneticAlgorithmLibrary.Chromosome.IntegerChromosome;

import java.util.List;


public class IntegerNeighborMutation implements MutationMethod {

    @Override
    public void mutate(List<Chromosome> chromosomes, double mutationRate) {
        for (Chromosome chromosome : chromosomes) {

            if (!(chromosome instanceof IntegerChromosome))
                continue;

            IntegerChromosome intChr = (IntegerChromosome) chromosome;
            int[] genes = (int[]) intChr.getGenes();
            int min = intChr.getMinValue();
            int max = intChr.getMaxValue();

            for (int i = 0; i < genes.length; i++) {
                if (Math.random() < mutationRate) {
                    int change = Math.random() < 0.5 ? -1 : 1;
                    genes[i] = Math.max(min, Math.min(max, genes[i] + change));
                }
            }

            intChr.setGenes(genes);
        }
    }
}
