# GA Core Engine - Overall Algorithm & Population Initialization

This project implements the core engine for a Genetic Algorithm library, focusing on the overall algorithm structure and population initialization.

## 🎯 Features Implemented

### Core Classes
- **Chromosome Interface**: Base interface for all chromosome types
- **BinaryChromosome**: Chromosomes with binary genes (0 or 1)
- **IntegerChromosome**: Chromosomes with integer genes within specified range
- **FloatChromosome**: Chromosomes with floating-point genes within specified range
- **Population**: Manages collections of chromosomes with utility methods
- **GeneticAlgorithm**: Main GA engine with complete algorithm flow

### GA Components (Interfaces)
- **FitnessFunction**: Interface for fitness evaluation
- **SelectionMethod**: Interface for parent selection
- **CrossoverMethod**: Interface for crossover operations
- **MutationMethod**: Interface for mutation operations
- **ReplacementStrategy**: Interface for population replacement
- **InfeasibilityHandler**: Interface for handling infeasible solutions

### Sample Implementations
- **SumFitnessFunction**: Maximizes sum of chromosome genes
- **RandomSelection**: Random parent selection
- **SinglePointCrossover**: Basic crossover implementation
- **RandomMutation**: Random mutation for all chromosome types
- **GenerationalReplacement**: Complete population replacement

## 🚀 How to Run

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Java compiler (javac) and runtime (java) in PATH

### Compilation and Execution
```bash
# Compile all Java files
javac *.java

# Run the demo
java GADemo
```

### Expected Output
```
=== GA Core Engine Demo ===
Testing Overall Algorithm & Population Initialization

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
   - **Selection**: Choose parents from population
   - **Crossover**: Generate offspring from parents
   - **Mutation**: Apply mutations to offspring
   - **Infeasibility Handling**: Repair infeasible solutions
   - **Replacement**: Update population with offspring
   - **Evaluation**: Calculate fitness for new population
   - **Best Tracking**: Update best solution found

## 🔧 Key Features

### Modular Design
- All GA components are interfaces, allowing easy swapping
- Chromosome types are polymorphic through common interface
- Clean separation of concerns

### Population Initialization
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
- Population initialization
- Algorithm execution
- Fitness improvement over generations
- Final solution quality

## 🎯 Deliverables Completed

✅ **Class GeneticAlgorithm.java**: Complete GA engine with all required methods
✅ **Class Population.java**: Population management with utility methods
✅ **Working Demo**: Comprehensive test demonstrating all functionality
✅ **Modular Design**: All components are interfaces for future extensibility
✅ **Three Chromosome Types**: Binary, Integer, and Float implementations
✅ **Complete Algorithm Flow**: Initialization → Evaluation → Selection → Crossover → Mutation → Replacement

## 🔮 Future Extensions

This core engine provides the foundation for:
- Advanced selection methods (tournament, roulette wheel)
- Sophisticated crossover strategies (uniform, arithmetic)
- Complex mutation operators
- Elitism and steady-state replacement strategies
- Constraint handling mechanisms
- Multi-objective optimization support
