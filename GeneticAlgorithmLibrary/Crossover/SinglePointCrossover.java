package GeneticAlgorithmLibrary.Crossover;

import GeneticAlgorithmLibrary.Chromosome.Chromosome;
import java.util.ArrayList;
import java.util.List;


public class SinglePointCrossover implements CrossoverMethod {
    @Override
    public List<Chromosome> crossover(List<Chromosome> parents, double crossoverRate) {
        List<Chromosome> offspring = new ArrayList<>();
        if (parents.size() < 2) return offspring;

        Chromosome p1 = parents.get(0);
        Chromosome p2 = parents.get(1);

        if (Math.random() > crossoverRate) {
            offspring.add(p1.clone());
            offspring.add(p2.clone());
            return offspring;
        }

        Object g1 = p1.getGenes();
        Object g2 = p2.getGenes();

        int length = p1.getLength();
        int point = (int) (Math.random() * length);

        if (g1 instanceof int[] && g2 instanceof int[]) {
            int[] a = (int[]) g1;
            int[] b = (int[]) g2;
            int[] child1 = new int[length];
            int[] child2 = new int[length];

            for (int i = 0; i < length; i++) {
                if (i < point) {
                    child1[i] = a[i];
                    child2[i] = b[i];
                } else {
                    child1[i] = b[i];
                    child2[i] = a[i];
                }
            }

            p1.setGenes(child1);
            p2.setGenes(child2);
        }

        offspring.add(p1.clone());
        offspring.add(p2.clone());
        return offspring;
    }
}
