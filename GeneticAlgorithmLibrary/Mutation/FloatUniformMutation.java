package GeneticAlgorithmLibrary.Mutation;

import GeneticAlgorithmLibrary.Chromosome.Chromosome;
import GeneticAlgorithmLibrary.Chromosome.FloatChromosome;

import java.util.List;


public class FloatUniformMutation implements MutationMethod {

    private final double noiseScale;

    public FloatUniformMutation() {
        this(0.05);
    }

    public FloatUniformMutation(double noiseScale) {
        this.noiseScale = noiseScale;
    }

    @Override
    public void mutate(List<Chromosome> chromosomes, double mutationRate) {
        for (Chromosome chromosome : chromosomes) {
            if (!(chromosome instanceof FloatChromosome))
                continue;

            FloatChromosome flt = (FloatChromosome) chromosome;
            double[] genes = (double[]) flt.getGenes();
            double min = flt.getMinValue();
            double max = flt.getMaxValue();
            double maxStep = (max - min) * noiseScale;

            for (int i = 0; i < genes.length; i++) {
                if (Math.random() < mutationRate) {
                    double step = (Math.random() * 2 - 1) * maxStep; 
                    genes[i] = Math.max(min, Math.min(max, genes[i] + step));
                }
            }

            flt.setGenes(genes);
        }
    }
}
