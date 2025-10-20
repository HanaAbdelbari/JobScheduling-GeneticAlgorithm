package GeneticAlgorithmLibrary.Chromosome;


public class FloatChromosome extends Chromosome {
    private double[] genes;
    private double minValue;
    private double maxValue;
    private int seed;


    public FloatChromosome(int length, double minValue, double maxValue, int seed) {
        super(length);
        if (minValue >= maxValue) {
            throw new IllegalArgumentException("minValue must be less than maxValue");
        }
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.genes = new double[length];
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
            throw new IllegalArgumentException("genes cannot be null");
        }
        if (genes instanceof double[]) {
            double[] arr = (double[]) genes;
            if (arr.length != this.length) {
                throw new IllegalArgumentException("Array length mismatch");
            }
            this.genes = new double[length];
            copyArray(arr, this.genes, length);
        } else {
            throw new IllegalArgumentException("Expected double[]");
        }
    }


     // Initialize chromosome randomly with values within [minValue, maxValue] using LCG.
    @Override
    public void initialize() {
        double range = maxValue - minValue;
        for (int i = 0; i < length; i++) {
            //separate the random value (rnd) from the full seed update, so the pseudo-random generator works as expected.
            int rnd = nextRandom(seed, 1000); // Get random value
            seed = (1103515245 * seed + 12345) & 0x7fffffff; // Advance full seed state
            double fraction = (double) rnd / 1000.0; // Convert to [0, 1)
            genes[i] = minValue + range * fraction;
        }
    }

    @Override
    public Chromosome clone() {
        FloatChromosome copy = new FloatChromosome(this.length, this.minValue, this.maxValue, this.seed);
        copyArray(this.genes, copy.genes, this.length);
        copy.fitness = this.fitness; // Copy fitness
        return copy;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("FloatChromosome: [");
        for (int i = 0; i < length; i++) {
            sb.append(genes[i]);
            if (i < length - 1) {
                sb.append(", ");
            }
        }
        sb.append("] | fitness=").append(fitness);
        return sb.toString();
    }


    public double getMinValue() {
        return minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }
}