package JobSchedulingCaseStudy;

import GeneticAlgorithmLibrary.*;
import GeneticAlgorithmLibrary.Chromosome.*;
import GeneticAlgorithmLibrary.Selection.*;
import GeneticAlgorithmLibrary.Crossover.*;
import GeneticAlgorithmLibrary.Mutation.*;
import GeneticAlgorithmLibrary.Replacement.*;

import java.util.Scanner;

import static JobSchedulingCaseStudy.ScheduleTranslator.printSchedule;


public class JobSchedulingDemo {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Genetic Algorithm for Job Scheduling ===\n");
        System.out.println("Do you want to enter your own values or use defaults?");
        System.out.println("1. Enter my own values");
        System.out.println("2. Use default configuration");
        System.out.print("Choice: ");
        int choice = scanner.nextInt();

        //  Problem setup
        int chromosomeLength;
        double[] processingTimes;
        int numberOfMachines;
        double capacity;

        int populationSize;
        int generations;
        int numParents;
        double crossoverRate;
        double mutationRate;

        SelectionMethod selectionMethod;
        CrossoverMethod crossoverMethod;
        MutationMethod mutationMethod;
        ReplacementMethod replacementMethod;
        Chromosome prototype;

        // Default configs
        chromosomeLength = 10;
        processingTimes = new double[]{3, 5, 2, 7, 4, 6, 8, 3, 9, 2};
        numberOfMachines = 4;
        capacity = 17.0;

        populationSize = 10;
        generations = 5;
        numParents = 2;
        crossoverRate = 0.7;
        mutationRate = 0.02;

        selectionMethod = new TournamentSelection(3);
        crossoverMethod = new SinglePointCrossover();
        mutationMethod = new IntegerNeighborMutation();
        replacementMethod = new ElitistReplacement(2);
        prototype = new IntegerChromosome(chromosomeLength, 0, numberOfMachines - 1, 42);

        // Optional manual input
        if (choice == 1) {
            System.out.print("Enter population size: ");
            populationSize = scanner.nextInt();

            System.out.print("Enter number of generations: ");
            generations = scanner.nextInt();

            System.out.print("Enter number of parents to select: ");
            numParents = scanner.nextInt();

            System.out.print("Enter crossover rate (0.0 - 1.0): ");
            crossoverRate = scanner.nextDouble();

            System.out.print("Enter mutation rate (0.0 - 1.0): ");
            mutationRate = scanner.nextDouble();

            // Selection Method
            System.out.println("\nSelect Selection Method:");
            System.out.println("1. Random Selection");
            System.out.println("2. Tournament Selection");
            System.out.println("3. Roulette Wheel Selection");
            System.out.print("Choice: ");
            int selectionChoice = scanner.nextInt();

            switch (selectionChoice) {
                case 1 -> selectionMethod = new RandomSelection(numParents);
                case 2 -> {
                    System.out.print("Enter tournament size: ");
                    int tSize = scanner.nextInt();
                    selectionMethod = new TournamentSelection(tSize);
                }
                case 3 -> selectionMethod = new RouletteWheelSelection();
                default -> throw new IllegalArgumentException("Invalid selection method choice");
            }

            //Crossover Method
            System.out.println("\nSelect Crossover Method:");
            System.out.println("1. Single-Point Crossover");
            System.out.println("2. Two-Point Crossover");
            System.out.println("3. Uniform Crossover");
            System.out.print("Choice: ");
            int crossoverChoice = scanner.nextInt();

            switch (crossoverChoice) {
                case 1 -> crossoverMethod = new SinglePointCrossover();
                case 2 -> crossoverMethod = new TwoPointCrossover();
                case 3 -> crossoverMethod = new UniformCrossover();
                default -> throw new IllegalArgumentException("Invalid crossover method choice");
            }

            //Mutation Method
            System.out.println("\nSelect Mutation Method:");
            System.out.println("1. Integer Neighbor Mutation");
            System.out.println("2. Bit Flip Mutation");
            System.out.println("3. Float Uniform Mutation");
            System.out.print("Choice: ");
            int mutationChoice = scanner.nextInt();

            switch (mutationChoice) {
                case 1 -> mutationMethod = new IntegerNeighborMutation();
                case 2 -> mutationMethod = new BitFlipMutation();
                case 3 -> mutationMethod = new FloatUniformMutation();
                default -> throw new IllegalArgumentException("Invalid mutation method choice");
            }

            //Replacement Method
            System.out.println("\nSelect Replacement Method:");
            System.out.println("1. Generational Replacement");
            System.out.println("2. Steady-State Replacement");
            System.out.println("3. Elitist Replacement");
            System.out.print("Choice: ");
            int replacementChoice = scanner.nextInt();

            switch (replacementChoice) {
                case 1 -> replacementMethod = new GenerationalReplacement();
                case 2 -> {
                    System.out.print("Enter number of individuals to replace: ");
                    int k = scanner.nextInt();
                    replacementMethod = new SteadyStateReplacement(k);
                }
                case 3 -> {
                    System.out.print("Enter number of elites to preserve: ");
                    int elites = scanner.nextInt();
                    replacementMethod = new ElitistReplacement(elites);
                }
                default -> throw new IllegalArgumentException("Invalid replacement method choice");
            }

            //Chromosome Type
            System.out.println("\nSelect Chromosome Type:");
            System.out.println("1. Integer Chromosome");
            System.out.println("2. Binary Chromosome");
            System.out.println("3. Float Chromosome");
            System.out.print("Choice: ");
            int chromoChoice = scanner.nextInt();

            switch (chromoChoice) {
                case 1 -> prototype = new IntegerChromosome(chromosomeLength, 0, numberOfMachines - 1, 42);
                case 2 -> prototype = new BinaryChromosome(chromosomeLength, 42);
                case 3 -> prototype = new FloatChromosome(chromosomeLength, 0.0, 1.0, 42);
                default -> throw new IllegalArgumentException("Invalid chromosome type choice");
            }
        } else {
            System.out.println("\nUsing default configuration...\n");
        }

        //Fitness function
        FitnessFunction fitnessFunction =
                new JobSchedulingFitness(processingTimes, numberOfMachines, capacity);

        //Create and configure GA Engine
        GAEngine ga = new GAEngine(
                populationSize,
                generations,
                crossoverRate,
                mutationRate,
                prototype,
                fitnessFunction
        );

        ga.setSelectionMethod(selectionMethod);
        ga.setCrossoverMethod(crossoverMethod);
        ga.setMutationMethod(mutationMethod);
        ga.setReplacementMethod(replacementMethod);


        System.out.println("\n Running Genetic Algorithm...\n");
        Chromosome best = ga.run();


        System.out.println("\n Finished!");
        System.out.println("Best individual found:\n" + best);
        System.out.println("Best fitness: " + String.format("%.4f", best.getFitness()));


        if (best instanceof IntegerChromosome ic) {
            printSchedule(ic, processingTimes, numberOfMachines);
        }

        scanner.close();
    }
}
