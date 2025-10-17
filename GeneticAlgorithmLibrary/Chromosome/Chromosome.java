package GeneticAlgorithmLibrary.Chromosome;

// Implements Cloneable so chromosomes can be safely copied
public abstract class Chromosome implements Cloneable {
    protected int length;
    protected double fitness = 0.0;

    public Chromosome(int length) {
        this.length = length;
    }

    public abstract Object getGenes();
    public abstract void setGenes(Object genes);
    public abstract void initialize();

    public int getLength() {
        return length;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    @Override
    public abstract Chromosome clone();  // Abstract clone method to enforce implementation

    @Override
    public abstract String toString();

    protected void copyArray(Object source, Object destination, int length) {
        if (source instanceof boolean[] && destination instanceof boolean[]) {
            boolean[] src = (boolean[]) source;
            boolean[] dest = (boolean[]) destination;
            for (int i = 0; i < length; i++) {
                dest[i] = src[i];
            }
        } else if (source instanceof int[] && destination instanceof int[]) {
            int[] src = (int[]) source;
            int[] dest = (int[]) destination;
            for (int i = 0; i < length; i++) {
                dest[i] = src[i];
            }
        } else if (source instanceof double[] && destination instanceof double[]) {
            double[] src = (double[]) source;
            double[] dest = (double[]) destination;
            for (int i = 0; i < length; i++) {
                dest[i] = src[i];
            }
        } else {
            throw new IllegalArgumentException("Unsupported array type for copying");
        }
    }

    protected int nextRandom(int seed, int max) {
        seed = (1103515245 * seed + 12345) & 0x7fffffff; // LCG parameters
        return seed % max;
    }
}