/**
 * Integer chromosome implementation where each gene is an integer within a specified range
 */
public class IntegerChromosome implements Chromosome {
    private int[] genes;
    private double fitness;
    private int minValue;
    private int maxValue;
    
    /**
     * Constructor for integer chromosome
     * @param length the number of genes in the chromosome
     * @param minValue minimum value for each gene
     * @param maxValue maximum value for each gene
     */
    public IntegerChromosome(int length, int minValue, int maxValue) {
        this.genes = new int[length];
        this.fitness = 0.0;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }
    
    @Override
    public void randomInitialize() {
        for (int i = 0; i < genes.length; i++) {
            genes[i] = minValue + (int)(Math.random() * (maxValue - minValue + 1));
        }
    }
    
    @Override
    public double getFitness() {
        return fitness;
    }
    
    @Override
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
    
    @Override
    public int getLength() {
        return genes.length;
    }
    
    @Override
    public Chromosome copy() {
        IntegerChromosome copy = new IntegerChromosome(genes.length, minValue, maxValue);
        System.arraycopy(genes, 0, copy.genes, 0, genes.length);
        copy.fitness = this.fitness;
        return copy;
    }
    
    /**
     * Get the gene at specific index
     * @param index the gene index
     * @return gene value
     */
    public int getGene(int index) {
        return genes[index];
    }
    
    /**
     * Set the gene at specific index
     * @param index the gene index
     * @param value the gene value
     */
    public void setGene(int index, int value) {
        genes[index] = value;
    }
    
    /**
     * Get minimum value for genes
     * @return minimum value
     */
    public int getMinValue() {
        return minValue;
    }
    
    /**
     * Get maximum value for genes
     * @return maximum value
     */
    public int getMaxValue() {
        return maxValue;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < genes.length; i++) {
            sb.append(genes[i]);
            if (i < genes.length - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
