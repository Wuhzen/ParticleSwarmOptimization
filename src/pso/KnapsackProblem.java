package pso;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class implements the fitness function evaluating
 * the knapsack problem.
 */
public class KnapsackProblem extends FitnessFunction{
    
    static {
        /* Registers itself in the factory. */
        FitnessFunctionFactory.registerFitnessFunction("knapsack", new KnapsackProblem());        
    }
    
    private static final double particlePositionMin = -0.5;
    private static final double particlePositionMax = 1.5;
    
    private ArrayList<Package> packages;
    
    public KnapsackProblem() {
        super(particlePositionMin, particlePositionMax);
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
    
    /**
     * Parses the input file specifing the properties of the packages
     * @param path Path to the file
     */
    void parsePackagesFile(String path) throws FileNotFoundException {
        File f = new File(path);        
        Scanner scanner = new Scanner(f);
        
        while(scanner.hasNextLine()) {
            processLine(scanner.next());
        }
    }
    
    /**
     * Processes one line from packages file and saves the properties
     * to the 'packages' structure 
     * @param line 
     */
    void processLine(String line) {
        double value, weight, volume;
        
        Scanner scanner = new Scanner(line);
        scanner.useDelimiter(",");
        
        if(scanner.hasNext()) {
            
        }
        
    }
}
