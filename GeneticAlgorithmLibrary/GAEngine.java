package GeneticAlgorithmLibrary;

import GeneticAlgorithmLibrary.Chromosome.Chromosome;
import GeneticAlgorithmLibrary.Selection.SelectionMethod;
import GeneticAlgorithmLibrary.Crossover.CrossoverMethod;
import GeneticAlgorithmLibrary.Mutation.MutationMethod;
import GeneticAlgorithmLibrary.Replacement.ReplacementMethod;

import java.util.List;
import java.util.Random;

/**
 * Core Genetic Algorithm Engine — clean version using only existing library classes.
 */
public class GAEngine {

    private final int populationSize;
    private final int generations;
    private final double crossoverRate;
    private final double mutationRate;

    private SelectionMethod selectionMethod;
    private CrossoverMethod crossoverMethod;
    private MutationMethod mutationMethod;
    private ReplacementMethod replacementMethod;

    private final FitnessFunction fitnessFunction;
    private final Chromosome prototype;
    private Population population;
    private final Random rand = new Random();

    public GAEngine(int populationSize, int generations, double crossoverRate,
                    double mutationRate, Chromosome prototype,
                    FitnessFunction fitnessFunction) {
        this.populationSize = populationSize;
        this.generations = generations;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.prototype = prototype;
        this.fitnessFunction = fitnessFunction;
    }

    // --- Configuration setters ---
    public void setSelectionMethod(SelectionMethod selectionMethod) {
        this.selectionMethod = selectionMethod;
    }

    public void setCrossoverMethod(CrossoverMethod crossoverMethod) {
        this.crossoverMethod = crossoverMethod;
    }

    public void setMutationMethod(MutationMethod mutationMethod) {
        this.mutationMethod = mutationMethod;
    }

    public void setReplacementMethod(ReplacementMethod replacementMethod) {
        this.replacementMethod = replacementMethod;
    }

    // --- Population initialization ---
    private void initializePopulation() {
        population = new Population(populationSize);
        for (int i = 0; i < populationSize; i++) {
            Chromosome individual = prototype.clone();
            // Assign a unique seed to each clone to diversify initialization
            individual.setSeed(rand.nextInt() & 0x7fffffff);
            individual.initialize();
            population.add(individual);
        }
    }

    // --- Fitness evaluation ---
    private void evaluatePopulation(Population pop) {
        for (Chromosome c : pop.getIndividuals()) {
            c.setFitness(fitnessFunction.evaluate(c));
        }
    }

    // --- Helper: build a Population from offspring list ---
    private Population buildPopulation(List<Chromosome> offspringList) {
        Population newPop = new Population(populationSize);
        for (Chromosome child : offspringList) {
            newPop.add(child);
        }
        return newPop;
    }

    // --- Main GA run cycle ---
    public Chromosome run() {
        if (selectionMethod == null || crossoverMethod == null ||
                mutationMethod == null || replacementMethod == null) {
            throw new IllegalStateException("All GA components must be set before running.");
        }

        initializePopulation();
        evaluatePopulation(population);

        System.out.println("\n=== Initial Population ===");
        int index = 1;
        for (Chromosome c : population.getIndividuals()) {
            System.out.println(index++ + ". " + c.toString());
        }
        System.out.println("===========================\n");

        Chromosome best = population.getBestIndividual().clone();

        System.out.println("Initial best fitness: " + String.format("%.4f", best.getFitness()));

        for (int gen = 1; gen <= generations; gen++) {
            // Selection
            List<Chromosome> parents = selectionMethod.select(population);

            // Crossover
            List<Chromosome> offspring = crossoverMethod.crossover(parents, crossoverRate);

            // Mutation
            mutationMethod.mutate(offspring, mutationRate);


            // transform offspring into a Population
            Population offspringPopulation = buildPopulation(offspring);

            // Replacement
            population = replacementMethod.replace(population, offspringPopulation);

            // Re-evaluate
            evaluatePopulation(population);

            // Track best
            Chromosome currentBest = population.getBestIndividual();
            if (currentBest.getFitness() > best.getFitness()) {
                best = currentBest.clone();
            }

            System.out.println("Generation " + gen + " → Best fitness: " + String.format("%.4f", best.getFitness()));
        }

        return best;
    }

}
