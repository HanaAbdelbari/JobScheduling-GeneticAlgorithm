package CaseStudies;

import FuzzyLogicLibrary.Defuzzification.CentroidDefuzzifier;
import FuzzyLogicLibrary.Defuzzification.MeanOfMaxDefuzzifier;
import FuzzyLogicLibrary.MembershipFunctions.TrapezoidialMF;
import FuzzyLogicLibrary.MembershipFunctions.TriangularMF;
import FuzzyLogicLibrary.Operators.MaxOr;
import FuzzyLogicLibrary.Operators.MinAnd;
import FuzzyLogicLibrary.Rules.FuzzyRule;
import FuzzyLogicLibrary.Rules.RuleAntecedent;
import FuzzyLogicLibrary.Rules.RuleBase;
import FuzzyLogicLibrary.Rules.RuleConsequent;
import FuzzyLogicLibrary.Variables.FuzzySet;
import FuzzyLogicLibrary.Variables.Input;
import FuzzyLogicLibrary.Variables.Output;
import FuzzyLogicLibrary.FuzzyLogicEngine.FuzzySystem;
import FuzzyLogicLibrary.FuzzyLogicEngine.FuzzyEvaluationResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HousePriceCaseStudy {

    public static void main(String[] args) {
        Input size = new Input("size", 50.0, 300.0);
        Input floor = new Input("floor", 0.0, 20.0);

        Output price = new Output("price", 300.0, 3000.0, 271);


        FuzzySet size_small  = new FuzzySet("size_small",  new TriangularMF(50.0, 50.0, 120.0));
        FuzzySet size_medium = new FuzzySet("size_medium", new TriangularMF(80.0, 150.0, 220.0));
        FuzzySet size_large  = new FuzzySet("size_large",  new TriangularMF(180.0, 300.0, 300.0));
        size.addFuzzySet(size_small);
        size.addFuzzySet(size_medium);
        size.addFuzzySet(size_large);

        FuzzySet floor_low  = new FuzzySet("floor_low",  new TriangularMF(0.0, 0.0, 5.0));
        FuzzySet floor_mid  = new FuzzySet("floor_mid",  new TriangularMF(3.0, 10.0, 15.0));
        FuzzySet floor_high = new FuzzySet("floor_high", new TriangularMF(10.0, 20.0, 20.0));
        floor.addFuzzySet(floor_low);
        floor.addFuzzySet(floor_mid);
        floor.addFuzzySet(floor_high);

        FuzzySet price_cheap     = new FuzzySet("price_cheap",     new TrapezoidialMF(300.0, 300.0, 600.0, 900.0));
        FuzzySet price_average   = new FuzzySet("price_average",   new TriangularMF(700.0, 1400.0, 2000.0));
        FuzzySet price_expensive = new FuzzySet("price_expensive", new TrapezoidialMF(1500.0, 2200.0, 3000.0, 3000.0));
        price.addFuzzySet(price_cheap);
        price.addFuzzySet(price_average);
        price.addFuzzySet(price_expensive);

        RuleBase rb = new RuleBase();


        rb.addRule(new FuzzyRule(
                new RuleAntecedent().addAndTerm("size_small").addAndTerm("floor_low"),
                RuleConsequent.mamdani(price_cheap)
        ));

        rb.addRule(new FuzzyRule(
                new RuleAntecedent().addAndTerm("size_small").addAndTerm("floor_mid"),
                RuleConsequent.mamdani(price_cheap)
        ));

        rb.addRule(new FuzzyRule(
                new RuleAntecedent().addAndTerm("size_small").addAndTerm("floor_high"),
                RuleConsequent.mamdani(price_average)
        ));

        rb.addRule(new FuzzyRule(
                new RuleAntecedent().addAndTerm("size_medium").addAndTerm("floor_low"),
                RuleConsequent.mamdani(price_average)
        ));

        rb.addRule(new FuzzyRule(
                new RuleAntecedent().addAndTerm("size_medium").addAndTerm("floor_mid"),
                RuleConsequent.mamdani(price_average)
        ));

        rb.addRule(new FuzzyRule(
                new RuleAntecedent().addAndTerm("size_medium").addAndTerm("floor_high"),
                RuleConsequent.mamdani(price_expensive)
        ));

        rb.addRule(new FuzzyRule(
                new RuleAntecedent().addAndTerm("size_large").addAndTerm("floor_low"),
                RuleConsequent.mamdani(price_average)
        ));

        rb.addRule(new FuzzyRule(
                new RuleAntecedent().addAndTerm("size_large").addAndTerm("floor_mid"),
                RuleConsequent.mamdani(price_expensive)
        ));

        rb.addRule(new FuzzyRule(
                new RuleAntecedent().addAndTerm("size_large").addAndTerm("floor_high"),
                RuleConsequent.mamdani(price_expensive)
        ));

        FuzzySystem fs = new FuzzySystem();
        fs.addInput(size);
        fs.addInput(floor);
        fs.setOutput(price);
        fs.setRuleBase(rb);

        fs.setAndOperand(new MinAnd());
        fs.setOrOperand(new MaxOr());


        Map<String, Double> crisp1 = new HashMap<>();
        crisp1.put("size", 160.0);
        crisp1.put("floor", 12.0);

        Map<String, Double> crisp2 = new HashMap<>();
        crisp2.put("size", 75.0);
        crisp2.put("floor", 2.0);

        double centroidResult1 = fs.evaluateMamdani(crisp1, new CentroidDefuzzifier());
        double momResult1 = fs.evaluateMamdani(crisp1, new MeanOfMaxDefuzzifier());

        double centroidResult2 = fs.evaluateMamdani(crisp2, new CentroidDefuzzifier());
        double momResult2 = fs.evaluateMamdani(crisp2, new MeanOfMaxDefuzzifier());

        FuzzyEvaluationResult detailed = fs.evaluateMamdaniDetailed(crisp1, new CentroidDefuzzifier());

        System.out.println("=== House Price Case Study (Mamdani) ===");
        System.out.println("Input A: size=160 m^2, floor=12");
        System.out.printf("Centroid defuzzifier => predicted price: %.2f EGP\n", centroidResult1);
        System.out.printf("Mean-of-Max defuzzifier => predicted price: %.2f EGP\n", momResult1);
        System.out.println();

        System.out.println("Input B: size=75 m^2, floor=2");
        System.out.printf("Centroid defuzzifier => predicted price: %.2f EGP\n", centroidResult2);
        System.out.printf("Mean-of-Max defuzzifier => predicted price: %.2f EGP\n", momResult2);
        System.out.println();

        System.out.println("---- Detailed evaluation for Input A ----");
        System.out.println("Memberships (set -> μ):");
        for (Map.Entry<String, Double> e : detailed.getMembershipsBySet().entrySet()) {
            System.out.printf("  %s -> %.4f\n", e.getKey(), e.getValue());
        }
        System.out.println();

        double[] strengths = detailed.getRuleStrengths();
        boolean[] enabled = detailed.getRuleEnabled();
        String[] consequents = detailed.getRuleConsequents();
        System.out.println("Rules (index : strength | consequent):");
        for (int i = 0; i < strengths.length; i++) {
            System.out.printf("  %d : %.4f | %s\n", i, strengths[i], consequents[i]);
        }
        System.out.println();

        System.out.println("Activations (Mamdani rules -> consequent set name and strength):");
        List< ? > activations = detailed.getActivations();
        for (int i = 0; i < activations.size(); i++) {
            var ra = (FuzzyLogicLibrary.Inference.InferenceEngine.RuleActivation) activations.get(i);
            System.out.printf("  %d : set=%s, strength=%.4f\n", i, ra.consequentSet.getName(), ra.strength);
        }
        System.out.println();

        System.out.println("Aggregated membership curve sample (first / middle / last points):");
        double[] universe = detailed.getUniverse();
        double[] aggregated = detailed.getAggregated();
        if (universe != null && aggregated != null && universe.length > 0) {
            int n = universe.length;
            System.out.printf("  x[0]=%.2f -> μ=%.4f\n", universe[0], aggregated[0]);
            System.out.printf("  x[mid]=%.2f -> μ=%.4f\n", universe[n/2], aggregated[n/2]);
            System.out.printf("  x[last]=%.2f -> μ=%.4f\n", universe[n-1], aggregated[n-1]);
        }

        System.out.println();
        System.out.printf("Final crisp (centroid) returned in detailed result: %.2f EGP\n", detailed.getCrisp());
    }
}
