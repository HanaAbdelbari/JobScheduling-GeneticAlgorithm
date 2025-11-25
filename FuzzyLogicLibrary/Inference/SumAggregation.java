package FuzzyLogicLibrary.Inference;

public class SumAggregation implements AggregationOperator {
    @Override
    public double aggregate(double aggregated, double newValue) {
        // Bounded sum: min(1, a + b)
        return Math.min(1.0, aggregated + newValue);
    }
}
