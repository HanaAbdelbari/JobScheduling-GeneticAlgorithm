import java.util.List;

/**
 * Simple random mutation method for demonstration
 * This is a basic implementation - more sophisticated methods will be added later
 */
public class RandomMutation implements MutationMethod {
    
    @Override
    public void mutate(List<Chromosome> chromosomes, double mutationRate) {
        for (Chromosome chromosome : chromosomes) {
            if (Math.random() < mutationRate) {
                // Perform mutation
                if (chromosome instanceof BinaryChromosome) {
                    mutateBinary((BinaryChromosome) chromosome);
                } else if (chromosome instanceof IntegerChromosome) {
                    mutateInteger((IntegerChromosome) chromosome);
                } else if (chromosome instanceof FloatChromosome) {
                    mutateFloat((FloatChromosome) chromosome);
                }
            }
        }
    }
    
    private void mutateBinary(BinaryChromosome chromosome) {
        int randomIndex = (int)(Math.random() * chromosome.getLength());
        int currentValue = chromosome.getGene(randomIndex);
        chromosome.setGene(randomIndex, 1 - currentValue); // Flip bit
    }
    
    private void mutateInteger(IntegerChromosome chromosome) {
        int randomIndex = (int)(Math.random() * chromosome.getLength());
        int minValue = chromosome.getMinValue();
        int maxValue = chromosome.getMaxValue();
        int newValue = minValue + (int)(Math.random() * (maxValue - minValue + 1));
        chromosome.setGene(randomIndex, newValue);
    }
    
    private void mutateFloat(FloatChromosome chromosome) {
        int randomIndex = (int)(Math.random() * chromosome.getLength());
        double minValue = chromosome.getMinValue();
        double maxValue = chromosome.getMaxValue();
        double newValue = minValue + Math.random() * (maxValue - minValue);
        chromosome.setGene(randomIndex, newValue);
    }
}
