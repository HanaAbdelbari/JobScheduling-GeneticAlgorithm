package GeneticAlgorithmLibrary.Chromosome;

public class BinaryChromosome extends Chromosome {
    private boolean[] genes;   //Uses a boolean[] array to represent genes.
    private int seed;          // internal seed for LCG-based randomness

    public BinaryChromosome(int length, int seed) {
        super(length);
        this.genes = new boolean[length];
        this.seed = seed;

        for (int i = 0; i < length; i++) {
            genes[i] = false;
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
        if (genes instanceof boolean[]) {
            boolean[] arr = (boolean[]) genes;
            if (arr.length != this.length) {
                throw new IllegalArgumentException("Array length mismatch");
            }
            this.genes = new boolean[length];
            copyArray(arr, this.genes, length);
        } else {
            throw new IllegalArgumentException("Expected boolean[]");
        }
    }


    @Override
    public void initialize() {
        for (int i = 0; i < length; i++) {
            seed = nextRandom(seed, 2);
            genes[i] = (seed == 1);
        }
    }

    @Override
    public Chromosome clone() {
        BinaryChromosome copy = new BinaryChromosome(this.length, this.seed);
        copyArray(this.genes, copy.genes, this.length);
        copy.fitness = this.fitness; // Copy fitness
        return copy;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("BinaryChromosome: ");
        for (boolean g : genes) {
            sb.append(g ? "1" : "0");
        }
        sb.append(" | fitness=").append(fitness);
        return sb.toString();
    }
}