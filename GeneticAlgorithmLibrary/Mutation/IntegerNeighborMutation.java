package GeneticAlgorithmLibrary.Mutation;

import GeneticAlgorithmLibrary.Chromosome.Chromosome;
import GeneticAlgorithmLibrary.Chromosome.IntegerChromosome;

import java.util.List;

/**
 * Integer neighbor mutation: pick a random gene and add either +1 or -1,
 * clamped within [minValue, maxValue]. Applies per chromosome with probability = mutationRate.
 */
public class IntegerNeighborMutation implements MutationMethod {

    @Override
    public void mutate(List<Chromosome> chromosomes, double mutationRate) {
        for (Chromosome chromosome : chromosomes) {
            if (chromosome instanceof IntegerChromosome) {
                IntegerChromosome intChr = (IntegerChromosome) chromosome;
                int[] genes = (int[]) intChr.getGenes();
                for (int i = 0; i < intChr.getLength(); i++) {
                    if (Math.random() < mutationRate) {
                        int delta = Math.random() < 0.5 ? -1 : 1;
                        int newVal = genes[i] + delta;
                        if (newVal < intChr.getMinValue()) newVal = intChr.getMinValue();
                        if (newVal > intChr.getMaxValue()) newVal = intChr.getMaxValue();
                        genes[i] = newVal;
                    }
                }
                intChr.setGenes(genes);
            }
        }
    }
}


