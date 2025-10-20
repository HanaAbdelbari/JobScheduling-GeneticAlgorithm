# GA Core Engine - Overall Algorithm & GeneticAlgorithmLibrary.Population Initialization

This project implements the core engine for a Genetic Algorithm library, focusing on the overall algorithm structure and population initialization.

## 🎯 Features Implemented

### Core Classes
- **GeneticAlgorithmLibrary.Chromosome.GeneticAlgorithmLibrary.Chromosome Interface**: Base interface for all chromosome types
- **GeneticAlgorithmLibrary.Chromosome.BinaryChromosome**: Chromosomes with binary genes (0 or 1)
- **GeneticAlgorithmLibrary.Chromosome.IntegerChromosome**: Chromosomes with integer genes within specified range
- **GeneticAlgorithmLibrary.Chromosome.FloatChromosome**: Chromosomes with floating-point genes within specified range
- **GeneticAlgorithmLibrary.Population**: Manages collections of chromosomes with utility methods
- **GeneticAlgorithmLibrary.GeneticAlgorithm**: Main GA engine with complete algorithm flow

### GA Components (Interfaces)
- **GeneticAlgorithmLibrary.Selection.Fitness.FitnessFunction**: Interface for fitness evaluation
- **GeneticAlgorithmLibrary.Selection.SelectionMethod**: Interface for parent selection
- **GeneticAlgorithmLibrary.Crossover.CrossoverMethod**: Interface for crossover operations
- **GeneticAlgorithmLibrary.Mutation.MutationMethod**: Interface for mutation operations
- **GeneticAlgorithmLibrary.Replacement.ReplacementStrategy**: Interface for population replacement
- **GeneticAlgorithmLibrary.InfeasibilityHandler**: Interface for handling infeasible solutions

### Sample Implementations
- **GeneticAlgorithmLibrary.SumFitnessFunction**: Maximizes sum of chromosome genes
- **GeneticAlgorithmLibrary.Selection.RandomSelection**: Random parent selection
- **GeneticAlgorithmLibrary.Crossover.SinglePointCrossover**: Basic crossover implementation
- **GeneticAlgorithmLibrary.Mutation.RandomMutation**: Random mutation for all chromosome types
- **GeneticAlgorithmLibrary.Replacement.GenerationalReplacement**: Complete population replacement

## 🚀 How to Run

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Java compiler (javac) and runtime (java) in PATH

### Compilation and Execution
```bash
# Compile all Java files
javac *.java

# Run the demo
java GeneticAlgorithmLibrary.GADemo
```

### Expected Output
```
=== GA Core Engine Demo ===
Testing Overall Algorithm & GeneticAlgorithmLibrary.Population Initialization

Testing Binary Chromosomes:
Objective: Maximize sum of genes (all 1s = best solution)

Initial Best fitness: 4.0000
Generation 0 → Best fitness: 4.0000
Generation 1 → Best fitness: 5.0000
...
Final Best Solution: [1,1,1,1,1,1,1,1,1,1]
Final Fitness: 10.0000
Expected Best: [1,1,1,1,1,1,1,1,1,1] with fitness 10.0
```

## 🧩 Algorithm Flow

The GA engine follows this complete flow:

1. **Initialization**: Create random population of specified size
2. **Evaluation**: Calculate fitness for all individuals
3. **Main Loop** (for each generation):
   - **GeneticAlgorithmLibrary.Selection**: Choose parents from population
   - **GeneticAlgorithmLibrary.Crossover**: Generate offspring from parents
   - **GeneticAlgorithmLibrary.Mutation**: Apply mutations to offspring
   - **Infeasibility Handling**: Repair infeasible solutions
   - **GeneticAlgorithmLibrary.Replacement**: Update population with offspring
   - **Evaluation**: Calculate fitness for new population
   - **Best Tracking**: Update best solution found

## 🔧 Key Features

### Modular Design
- All GA components are interfaces, allowing easy swapping
- GeneticAlgorithmLibrary.Chromosome.GeneticAlgorithmLibrary.Chromosome types are polymorphic through common interface
- Clean separation of concerns

### GeneticAlgorithmLibrary.Population Initialization
- Supports three chromosome types: Binary, Integer, Float
- Random initialization with proper bounds checking
- Configurable population size

### Algorithm Management
- Complete GA loop implementation
- Best solution tracking across generations
- Progress monitoring with generation-by-generation output
- Error handling for missing components

## 📊 Test Cases

The demo includes three test cases:

1. **Binary Chromosomes**: 10-bit chromosomes, maximize sum (target: all 1s)
2. **Integer Chromosomes**: 8 genes, range 0-5, maximize sum (target: all 5s)
3. **Float Chromosomes**: 6 genes, range 0.0-10.0, maximize sum (target: all 10.0s)

Each test demonstrates:
- GeneticAlgorithmLibrary.Population initialization
- Algorithm execution
- Fitness improvement over generations
- Final solution quality

## 🎯 Deliverables Completed

✅ **Class GeneticAlgorithmLibrary.GeneticAlgorithm.java**: Complete GA engine with all required methods
✅ **Class GeneticAlgorithmLibrary.Population.java**: GeneticAlgorithmLibrary.Population management with utility methods
✅ **Working Demo**: Comprehensive test demonstrating all functionality
✅ **Modular Design**: All components are interfaces for future extensibility
✅ **Three GeneticAlgorithmLibrary.Chromosome.GeneticAlgorithmLibrary.Chromosome Types**: Binary, Integer, and Float implementations
✅ **Complete Algorithm Flow**: Initialization → Evaluation → GeneticAlgorithmLibrary.Selection → GeneticAlgorithmLibrary.Crossover → GeneticAlgorithmLibrary.Mutation → GeneticAlgorithmLibrary.Replacement

## 🔮 Future Extensions

This core engine provides the foundation for:
- Advanced selection methods (tournament, roulette wheel)
- Sophisticated crossover strategies (uniform, arithmetic)
- Complex mutation operators
- Elitism and steady-state replacement strategies
- Constraint handling mechanisms
- Multi-objective optimization support
