package GeneticAlgorithmLibrary.Chromosome;

public class IntegerChromosome extends Chromosome {
    private int[] genes;
    private int minValue;
    private int maxValue;
    private int seed;

    public IntegerChromosome(int length, int minValue, int maxValue, int seed) {
        super(length);
        if (minValue > maxValue) {
            throw new IllegalArgumentException("MinValue must be less than or equal to MaxValue");
        }
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.genes = new int[length];
        this.seed = seed;

        for (int i = 0; i < length; i++) {
            genes[i] = minValue;
        }
    }

    @Override
    public Object getGenes() {
        return genes;
    }

    @Override
    public void setGenes(Object genes) {
        if (genes == null) {
            throw new IllegalArgumentException("Genes cannot be null");
        }
        if (genes instanceof int[]) {
            int[] arr = (int[]) genes;
            if (arr.length != this.length) {
                throw new IllegalArgumentException("Array length mismatch");
            }
            this.genes = new int[length];
            copyArray(arr, this.genes, length);
        } else {
            throw new IllegalArgumentException("Expected int[]");
        }
    }


    @Override
    public void initialize() {
        int range = maxValue - minValue + 1; // Inclusive range
        for (int i = 0; i < length; i++) {
            //Uses nextRandom to get pseudo-random values; seed is advanced within nextRandom( updated internally).
            int rnd = nextRandom(seed, range);
            genes[i] = minValue + rnd; // Assign integer value
        }
    }

    @Override
    public Chromosome clone() {
        IntegerChromosome copy = new IntegerChromosome(this.length, this.minValue, this.maxValue, this.seed);
        copyArray(this.genes, copy.genes, this.length);
        copy.fitness = this.fitness;
        return copy;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("IntegerChromosome: [");
        for (int i = 0; i < length; i++) {
            sb.append(genes[i]);
            if (i < length - 1) {
                sb.append(", ");
            }
        }
        sb.append("] | fitness=").append(fitness);
        return sb.toString();
    }


    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }
}