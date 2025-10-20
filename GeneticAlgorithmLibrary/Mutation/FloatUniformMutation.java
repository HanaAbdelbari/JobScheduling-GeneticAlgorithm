package GeneticAlgorithmLibrary.Mutation;

import GeneticAlgorithmLibrary.Chromosome.Chromosome;
import GeneticAlgorithmLibrary.Chromosome.FloatChromosome;

import java.util.List;

/**
 * Uniform Floating-Point Mutation (per lectures):
 * Adds small uniform noise to a random gene, then clamps to [min, max].
 * step = u * (UB-LB), where u in [-noiseScale, +noiseScale].
 */
public class FloatUniformMutation implements MutationMethod {

    private final double noiseScale;

    public FloatUniformMutation() {
        this(0.05); // default: 5% of the range
    }

    public FloatUniformMutation(double noiseScale) {
        this.noiseScale = noiseScale;
    }

    @Override
    public void mutate(List<Chromosome> chromosomes, double mutationRate) {
        for (Chromosome chromosome : chromosomes) {
            if (chromosome instanceof FloatChromosome) {
                FloatChromosome flt = (FloatChromosome) chromosome;
                double[] genes = (double[]) flt.getGenes();
                double range = flt.getMaxValue() - flt.getMinValue();
                double maxStep = range * noiseScale;
                for (int i = 0; i < flt.getLength(); i++) {
                    if (Math.random() < mutationRate) {
                        double step = (Math.random() * 2 - 1) * maxStep; // [-maxStep, +maxStep]
                        double newVal = genes[i] + step;
                        if (newVal < flt.getMinValue()) newVal = flt.getMinValue();
                        if (newVal > flt.getMaxValue()) newVal = flt.getMaxValue();
                        genes[i] = newVal;
                    }
                }
                flt.setGenes(genes);
            }
        }
    }
}


