package pso;

import java.util.ArrayList;

/**
 * This class implements the fitness function evaluating the circle problem.
 */
public class CircleProblem extends FitnessFunction {
    static {
        /* Registers itself in the factory. */
        FitnessFunctionFactory.registerFitnessFunction("circle", new CircleProblem());
    }
    
    private static final double particlePositionMin = -100;
    private static final double particlePositionMax = 100;

    public CircleProblem() {
        super(particlePositionMin, particlePositionMax);
    }        
    
    /**
     * Implementation of fitness function.
     * @param vector Particle's position
     * @return fitness value
     */
    @Override
    public double get(ArrayList<Double> vector) {
        double sum = 0;
        for (Double value : vector) {
            sum += value * value;
        }
        return sum;
    }

    /**
     * Creates the fitness function object
     * @return 
     */
    @Override
    public FitnessFunction createFitnessFunction() {
        return new CircleProblem();
    }
}
