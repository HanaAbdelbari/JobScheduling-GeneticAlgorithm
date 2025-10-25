package GeneticAlgorithmLibrary.Crossover;

import GeneticAlgorithmLibrary.Chromosome.Chromosome;
import java.util.ArrayList;
import java.util.List;

public class TwoPointCrossover implements CrossoverMethod {

    @Override
    public List<Chromosome> crossover(List<Chromosome> parents, double crossoverRate) {
        List<Chromosome> offspring = new ArrayList<>();

        for (int i = 0; i < parents.size(); i += 2) {
            Chromosome p1 = parents.get(i);
            Chromosome p2 = parents.get((i + 1) % parents.size());

            if (Math.random() > crossoverRate) {
                offspring.add(p1.clone());
                offspring.add(p2.clone());
                continue;
            }

            int len = p1.getLength();
            int pA = (int) (Math.random() * len);
            int pB = (int) (Math.random() * len);
            if (pA > pB) { int tmp = pA; pA = pB; pB = tmp; }

            Object g1 = p1.getGenes();
            Object g2 = p2.getGenes();

            if (g1 instanceof boolean[]) {
                boolean[] a = (boolean[]) g1;
                boolean[] b = (boolean[]) g2;
                for (int j = pA; j < pB; j++) {
                    boolean tmp = a[j]; a[j] = b[j]; b[j] = tmp;
                }
            } else if (g1 instanceof int[]) {
                int[] a = (int[]) g1;
                int[] b = (int[]) g2;
                for (int j = pA; j < pB; j++) {
                    int tmp = a[j]; a[j] = b[j]; b[j] = tmp;
                }
            } else if (g1 instanceof double[]) {
                double[] a = (double[]) g1;
                double[] b = (double[]) g2;
                for (int j = pA; j < pB; j++) {
                    double tmp = a[j]; a[j] = b[j]; b[j] = tmp;
                }
            }

            Chromosome c1 = p1.clone();
            Chromosome c2 = p2.clone();
            c1.setGenes(g1);
            c2.setGenes(g2);
            offspring.add(c1);
            offspring.add(c2);
        }

        return offspring;
    }
}
