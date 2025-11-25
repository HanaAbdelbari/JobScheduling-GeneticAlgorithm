package FuzzyLogicLibrary.Inference;

public class MaxAggregation implements AggregationOperator {
    @Override
    public double aggregate(double aggregated, double newValue) {
        return Math.max(aggregated, newValue);
        
    }
}
