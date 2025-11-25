package FuzzyLogicLibrary.FuzzyLogicEngine;

import FuzzyLogicLibrary.Defuzzification.CentroidDefuzzifier;
import FuzzyLogicLibrary.Defuzzification.MeanOfMaxDefuzzifier;
import FuzzyLogicLibrary.Defuzzification.DefuzzificationMethod;
import FuzzyLogicLibrary.MembershipFunctions.TriangularMF;
import FuzzyLogicLibrary.Operators.AndOperand;
import FuzzyLogicLibrary.Operators.MinAnd;
import FuzzyLogicLibrary.Rules.FuzzyRule;
import FuzzyLogicLibrary.Rules.RuleAntecedent;
import FuzzyLogicLibrary.Rules.RuleBase;
import FuzzyLogicLibrary.Rules.RuleConsequent;
import FuzzyLogicLibrary.Variables.FuzzySet;
import FuzzyLogicLibrary.Variables.Input;
import FuzzyLogicLibrary.Variables.Output;

import java.util.HashMap;
import java.util.Map;

public class FuzzySystemDemo {
    public static void main(String[] args) {
        // Define inputs
        Input temperature = new Input("temperature", 0, 40);
        Input speed = new Input("speed", 0, 120);

        // Define output
        Output throttle = new Output("throttle", 0.0, 1.0, 201);

        // Fuzzy sets (use unique names across all variables)
        // Temperature sets
        FuzzySet temp_cold = new FuzzySet("temp_cold", new TriangularMF(0, 0, 20));
        FuzzySet temp_warm = new FuzzySet("temp_warm", new TriangularMF(10, 20, 30));
        FuzzySet temp_hot  = new FuzzySet("temp_hot",  new TriangularMF(20, 40, 40));
        temperature.addFuzzySet(temp_cold);
        temperature.addFuzzySet(temp_warm);
        temperature.addFuzzySet(temp_hot);

        // Speed sets
        FuzzySet speed_slow   = new FuzzySet("speed_slow",   new TriangularMF(0, 0, 60));
        FuzzySet speed_medium = new FuzzySet("speed_medium", new TriangularMF(40, 60, 80));
        FuzzySet speed_fast   = new FuzzySet("speed_fast",   new TriangularMF(60, 120, 120));
        speed.addFuzzySet(speed_slow);
        speed.addFuzzySet(speed_medium);
        speed.addFuzzySet(speed_fast);

        // Throttle sets (for Mamdani consequents)
        FuzzySet thr_low    = new FuzzySet("thr_low",    new TriangularMF(0.0, 0.0, 0.5));
        FuzzySet thr_medium = new FuzzySet("thr_medium", new TriangularMF(0.25, 0.5, 0.75));
        FuzzySet thr_high   = new FuzzySet("thr_high",   new TriangularMF(0.5, 1.0, 1.0));
        throttle.addFuzzySet(thr_low);
        throttle.addFuzzySet(thr_medium);
        throttle.addFuzzySet(thr_high);

        // Rule base
        RuleBase rb = new RuleBase();
        AndOperand andOp = new MinAnd();

        // Example Mamdani rules
        // 1) IF temp_cold AND speed_fast THEN thr_medium
        rb.addRule(new FuzzyRule(
                new RuleAntecedent().addTerm("temp_cold").addTerm("speed_fast"),
                RuleConsequent.mamdani(thr_medium)
        ));
        // 2) IF temp_hot AND speed_slow THEN thr_high
        rb.addRule(new FuzzyRule(
                new RuleAntecedent().addTerm("temp_hot").addTerm("speed_slow"),
                RuleConsequent.mamdani(thr_high)
        ));
        // 3) IF temp_warm AND speed_medium THEN thr_low
        rb.addRule(new FuzzyRule(
                new RuleAntecedent().addTerm("temp_warm").addTerm("speed_medium"),
                RuleConsequent.mamdani(thr_low)
        ));

        // Example Sugeno zero-order rules (constants)
        rb.addRule(new FuzzyRule(
                new RuleAntecedent().addTerm("temp_cold").addTerm("speed_slow"),
                RuleConsequent.sugenoConstant(0.3)
        ));
        rb.addRule(new FuzzyRule(
                new RuleAntecedent().addTerm("temp_warm").addTerm("speed_medium"),
                RuleConsequent.sugenoConstant(0.5)
        ));
        rb.addRule(new FuzzyRule(
                new RuleAntecedent().addTerm("temp_hot").addTerm("speed_fast"),
                RuleConsequent.sugenoConstant(0.8)
        ));

        // Build system
        FuzzySystem fs = new FuzzySystem();
        fs.addInput(temperature);
        fs.addInput(speed);
        fs.setOutput(throttle);
        fs.setRuleBase(rb);
        fs.setAndOperand(andOp);

        // Sample crisp inputs
        Map<String, Double> crisp = new HashMap<>();
        crisp.put("temperature", 18.0);
        crisp.put("speed", 70.0);

        // Evaluate Mamdani with centroid and mean-of-max
        DefuzzificationMethod centroid = new CentroidDefuzzifier();
        DefuzzificationMethod mom = new MeanOfMaxDefuzzifier();
        double mamdaniCentroid = fs.evaluateMamdani(crisp, centroid);
        double mamdaniMoM = fs.evaluateMamdani(crisp, mom);

        // Evaluate Sugeno zero-order (weighted average)
        double sugeno = fs.evaluateSugenoZeroOrder(crisp, null);

        // Print results
        System.out.println("Fuzzy System Demo\n-------------------");
        System.out.println("Inputs: temperature=18.0, speed=70.0");
        System.out.println("Mamdani (Centroid): " + mamdaniCentroid);
        System.out.println("Mamdani (Mean of Maxima): " + mamdaniMoM);
        System.out.println("Sugeno (Weighted Average): " + sugeno);
    }
}
