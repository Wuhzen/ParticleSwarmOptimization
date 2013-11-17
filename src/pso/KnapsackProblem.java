package pso;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * This class implements the fitness function evaluating the knapsack problem.
 */
public class KnapsackProblem extends FitnessFunction {

    static {
        /* Registers itself in the factory. */
        FitnessFunctionFactory.registerFitnessFunction("knapsack", new KnapsackProblem());
    }
    
    private static final double clampMin = 
            CommonConstants.knapsackProblemClampVelocityMin;
    private static final double clampMax = 
            CommonConstants.knapsackProblemClampVelocityMax;
    private ArrayList<Package> packages;
    private double weightLimit;


    public KnapsackProblem() {
        super(CommonConstants.knapsackProblemParticlePositionMin, 
              CommonConstants.knapsackProblemParticlePositionMax,
              clampMin, clampMax);
        packages = new ArrayList<>();
    }
   
    public void setWeightLimit(double weightLimit) {
        this.weightLimit = weightLimit;
    }
    
    /**
     * Implementation of fitness function.
     *
     * @param vector
     * @return fitness value
     */
    @Override
    public double get(ArrayList<Double> vector) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Creates the fitness function object
     *
     * @return
     */
    @Override
    public FitnessFunction createFitnessFunction() {
        return new KnapsackProblem();
    }

    /**
     * Parses the input file specifing the properties of the packages
     *
     * @param path Path to the file
     */
    void parsePackagesFile(String path, boolean considerVolume) {
        File f;
        Scanner scanner = null;

        try {
            f = new File(path);
            scanner = new Scanner(f);

            if (!scanner.hasNext()) {
                throw new KnapsackPackagesFileEmptyException();
            }

            while (scanner.hasNextLine()) {
                processLine(scanner.next(), considerVolume);
            }
        } catch (NullPointerException e) {
            System.err.println("ERROR: No file specified.\n");
            throw e;
        } catch (KnapsackPackagesFileEmptyException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException();
        } catch (FileNotFoundException e) {
            System.err.println("ERROR: File " + path + " was not found.\n");
            throw new RuntimeException(e);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    /**
     * Processes one line from packages file and saves the properties to the
     * 'packages' structure
     *
     * @param line
     */
    void processLine(String line, boolean considerVolume) {
        double value, weight, volume = 0.0;

        Scanner scanner = new Scanner(line);
        scanner.useDelimiter(",");

        try {
            value = scanner.nextDouble();
            weight = scanner.nextDouble();

            if (considerVolume) {
                volume = scanner.nextDouble();
            }

            packages.add(new Package(value, weight, volume));
        } catch (NoSuchElementException e) {
            System.err.println("ERROR: Wrong file content");
            throw e;
        }
    }

    public void printPackages() {
        for (Package p : packages) {
            System.out.println("package: ");
            System.out.println(p.value);
            System.out.println(p.weight);
            System.out.println(p.volume);
        }
        System.exit(0);
    }

    /**
     * @return Returns the weight of the heaviest package
     */
    public double getMaxPackageWeight() {
        double maxWeight = 0;

        for (Package p : packages) {
            if (p.weight > maxWeight) {
                maxWeight = p.weight;
            }
        }

        return maxWeight;
    }

    @Override
    public ArrayList<Double> initParticlePosition(int dimension) {
        double maxPackageWeight = getMaxPackageWeight();
        int    minFitPackages   = (int) (weightLimit / maxPackageWeight);
        int    i;
        
        ArrayList<Double> position = new ArrayList<>(dimension);
        for(i = 0; i < dimension; i++) {
            position.add(0.0);
        }
        
        i = 0;
        while(i < minFitPackages) {
            int index = (int) getRandomNumber(0, dimension - Double.MIN_VALUE);            
            if(position.get(index) != 0.0) {
                continue;
            }            
            position.set(index, 1.0);
            i++;
        }
        
        return position;
    }

    @Override
    public ArrayList<Double> clampVelocity(ArrayList<Double> velocity) {
        int sign;
        
        for (int i = 0; i < velocity.size(); i++) {
            if (velocity.get(i) < clampMin) {
                velocity.set(i, clampMin);
            } else if (velocity.get(i) > clampMax) {
                velocity.set(i, clampMax);
            }
            
            // Map to range <-1, 1> using sigmoid function        
            sign = (velocity.get(i) > 0.0) ? 1 : -1;            
            velocity.set(i, sign * sigmoid(velocity.get(i)));
        }        
        
        return velocity;
    }    
    
    /**
     * Implements sigmoid function
     * @param arg
     * @return 
     */
    private double sigmoid(double arg) {
        return 1/(1 + Math.exp(-arg));
    }
}
/**
 * Excpetion thrown if the file specifing the packages properties is empty.
 */
class KnapsackPackagesFileEmptyException extends Exception {

    public KnapsackPackagesFileEmptyException() {
        super("ERROR: File with packages specification is empty.\n");
    }
}
