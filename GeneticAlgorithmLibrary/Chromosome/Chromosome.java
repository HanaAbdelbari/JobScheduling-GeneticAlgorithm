package GeneticAlgorithmLibrary.Chromosome;

public abstract class Chromosome implements Cloneable {
    protected int length;
    protected double fitness = 0.0;
    protected int seed;

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
            throw new IllegalArgumentException("unsupported array type for copying");
        }
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    // update nextRandom to use and persist internal seed
    protected int nextRandom(int max) {
        // LCG update stored in this.seed
        this.seed = (1103515245 * this.seed + 12345) & 0x7fffffff;
        return this.seed % max;
    }
}