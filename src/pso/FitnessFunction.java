package pso;

import java.util.ArrayList;

public abstract class FitnessFunction {
	protected double particlePostionMin;
	protected double particlePostionMax;
        
        public FitnessFunction(double particlePostionMin, 
                               double particlePostionMax) {
            
            this.particlePostionMin = particlePostionMin;
            this.particlePostionMax = particlePostionMax;
        }
    
        public double getParticlePositionMin() {
            return particlePostionMin;
        }
        
        public double getParticlePositionMax() {
            return particlePostionMax;
        }
        
        public abstract double get(final ArrayList<Double> vector);
        
        /**
         * Subclasses must be able to create the objects of themselves.
         * @return new instance
         */
        public abstract FitnessFunction createFitnessFunction();        
}
