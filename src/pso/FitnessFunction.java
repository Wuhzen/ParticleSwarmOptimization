package pso;

import java.util.ArrayList;

public abstract class FitnessFunction {
	public abstract double get(final ArrayList<Double> vector);
        
        /**
         * Subclasses must be able to create the objects of themselves.
         * @return new instance
         */
        public abstract FitnessFunction createFitnessFunction();        
}
