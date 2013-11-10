package pso;

import java.util.ArrayList;

/**
 * This class implements the fitness function evaluating
 * the knapsack problem.
 */
public class KnapsackProblem extends FitnessFunction{
    
    static {
        /* Registers itself in the factory. */
        FitnessFunctionFactory.registerFitnessFunction("knapsack", new CircleProblem());        
    }
    
    /**
     * Implementation of fitness function.
     * @param vector 
     * @return fitness value
     */
    @Override
    public double get(ArrayList<Double> vector) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    /**
     * Creates the fitness function object
     * @return 
     */
    @Override
    public FitnessFunction createFitnessFunction() {
        return new KnapsackProblem();
    }
    
    
}
