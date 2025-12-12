package CaseStudies;

import GeneticAlgorithmLibrary.Chromosome.IntegerChromosome;

public class ScheduleTranslator {

    public static void printSchedule(IntegerChromosome chromosome, double[] processingTimes, int numberOfMachines) {
        int[] assignments = (int[]) chromosome.getGenes();
        double[] machineLoads = new double[numberOfMachines];

        System.out.println("\n=== Job-to-Machine Assignment ===");

        for (int i = 0; i < assignments.length; i++) {
            int machine = assignments[i];
            double time = processingTimes[i];
            machineLoads[machine] += time;
            System.out.printf("Job %2d → Machine %d (Processing time: %.2f)%n", i + 1, machine, time);
        }

        System.out.println("\n=== Machine Loads ===");
        for (int m = 0; m < numberOfMachines; m++) {
            System.out.printf("Machine %d total load: %.2f%n", m, machineLoads[m]);
        }
        System.out.println("==============================\n");
    }

}
