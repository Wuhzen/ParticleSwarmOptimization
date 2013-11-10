package pso;

import java.util.HashMap;
import pso.FitnessFunction;

/**
 * This class defines the Factory Method which returns a product - an
 * object implementin specific fitness function. The class is created as a
 * Singleton.
 */
public class FitnessFunctionFactory {
    
    /* Only one instance of this class exists. */   
    private static final FitnessFunctionFactory instance 
            = new FitnessFunctionFactory();    
    
    /* All known objects implementing Fitness Function */
    private static HashMap<String, FitnessFunction> registeredFitnessFunctions 
            = new HashMap<>();

    
    private FitnessFunctionFactory() {
    }

    /**     
     * @return The only existing instance of this class.
     */
    public static FitnessFunctionFactory getInstance() {
        return instance;
    }

    /**
     * Registers the products.
     * @param id product id
     * @param f Product - Object implementning certain Fitness Function
     */
    public static void registerFitnessFunction(String id, FitnessFunction f) {
        registeredFitnessFunctions.put(id, f);
    }
    
    /**
     * Creates the product.
     * @param id product id
     * @return New product
     */
    public FitnessFunction createFitnessFunction(String id) {
        return registeredFitnessFunctions.get(id).createFitnessFunction();
    }
}
