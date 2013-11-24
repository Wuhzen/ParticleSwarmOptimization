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
    private static final double clampMin = 
            CommonConstants.circleProblemParticlePositionMin / CommonConstants.circleProblemVelocityClampConstant;
    private static final double clampMax = 
            CommonConstants.circleProblemParticlePositionMax / CommonConstants.circleProblemVelocityClampConstant;

    public CircleProblem() {
        super(CommonConstants.circleProblemParticlePositionMin,
              CommonConstants.circleProblemParticlePositionMax,
              clampMin, clampMax);
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
    
    @Override
    public ArrayList<Double> clampVelocity(ArrayList<Double> velocity) {
        for (int i = 0; i < velocity.size(); i++) {
            if (velocity.get(i) < clampMin) {
                velocity.set(i, clampMin);
            } else if (velocity.get(i) > clampMax) {
                velocity.set(i, clampMax);
            }
        }
    	   
        return velocity;
    }
    
    public static ArrayList<Double> pes(ArrayList<Double> velocity, Double clampMax) {
        /*for (int i = 0; i < velocity.size(); i++) {
            if (velocity.get(i) < clampMin) {
                velocity.set(i, clampMin);
            } else if (velocity.get(i) > clampMax) {
                velocity.set(i, clampMax);
            }
        }*/
    	
    	double length = 0.0;
    	for (int i = 0; i < velocity.size(); i++) {
    		length += velocity.get(i) * velocity.get(i);
    	}
    	
    	length = Math.sqrt(length);
    	
    	if (length > clampMax) {
    		double k = length/clampMax;
    		for (int i = 0; i < velocity.size(); i++) {
    			velocity.set(i, velocity.get(i) / k);
    		}
    	}
        
        return velocity;
    }

    @Override
    public ArrayList<Double> initParticlePosition(int dimension) {
        ArrayList<Double> position = new ArrayList<>();        
        position = getRandomList(dimension, 
                                 particlePostionMin, 
                                 particlePostionMax);        
        return position;
    }

    // Not neede here -> only returns the parameter it gets unchanged.
    @Override
    public ArrayList<Double> clampPosition(ArrayList<Double> position,
                                           ArrayList<Double> velocity) {
        return position;
    }
}
