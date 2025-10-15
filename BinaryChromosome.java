/**
 * Binary chromosome implementation where each gene is 0 or 1
 */
public class BinaryChromosome implements Chromosome {
    private int[] genes;
    private double fitness;
    
    /**
     * Constructor for binary chromosome
     * @param length the number of genes in the chromosome
     */
    public BinaryChromosome(int length) {
        this.genes = new int[length];
        this.fitness = 0.0;
    }
    
    @Override
    public void randomInitialize() {
        for (int i = 0; i < genes.length; i++) {
            genes[i] = Math.random() < 0.5 ? 0 : 1;
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
        BinaryChromosome copy = new BinaryChromosome(genes.length);
        System.arraycopy(genes, 0, copy.genes, 0, genes.length);
        copy.fitness = this.fitness;
        return copy;
    }
    
    /**
     * Get the gene at specific index
     * @param index the gene index
     * @return gene value (0 or 1)
     */
    public int getGene(int index) {
        return genes[index];
    }
    
    /**
     * Set the gene at specific index
     * @param index the gene index
     * @param value the gene value (0 or 1)
     */
    public void setGene(int index, int value) {
        genes[index] = value;
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
