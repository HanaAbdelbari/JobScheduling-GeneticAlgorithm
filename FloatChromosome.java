/**
 * Floating-point chromosome implementation where each gene is a real number within a specified range
 */
public class FloatChromosome implements Chromosome {
    private double[] genes;
    private double fitness;
    private double minValue;
    private double maxValue;
    
    /**
     * Constructor for floating-point chromosome
     * @param length the number of genes in the chromosome
     * @param minValue minimum value for each gene
     * @param maxValue maximum value for each gene
     */
    public FloatChromosome(int length, double minValue, double maxValue) {
        this.genes = new double[length];
        this.fitness = 0.0;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }
    
    @Override
    public void randomInitialize() {
        for (int i = 0; i < genes.length; i++) {
            genes[i] = minValue + Math.random() * (maxValue - minValue);
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
        FloatChromosome copy = new FloatChromosome(genes.length, minValue, maxValue);
        System.arraycopy(genes, 0, copy.genes, 0, genes.length);
        copy.fitness = this.fitness;
        return copy;
    }
    
    /**
     * Get the gene at specific index
     * @param index the gene index
     * @return gene value
     */
    public double getGene(int index) {
        return genes[index];
    }
    
    /**
     * Set the gene at specific index
     * @param index the gene index
     * @param value the gene value
     */
    public void setGene(int index, double value) {
        genes[index] = value;
    }
    
    /**
     * Get minimum value for genes
     * @return minimum value
     */
    public double getMinValue() {
        return minValue;
    }
    
    /**
     * Get maximum value for genes
     * @return maximum value
     */
    public double getMaxValue() {
        return maxValue;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < genes.length; i++) {
            sb.append(String.format("%.4f", genes[i]));
            if (i < genes.length - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
