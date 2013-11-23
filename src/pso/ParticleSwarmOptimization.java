package pso;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParticleSwarmOptimization {

    static {
        try {
            /* load all necessary classes */
            Class.forName("pso.CircleProblem");
            Class.forName("pso.KnapsackProblem");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ParticleSwarmOptimization.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // To use '.' as a decimal separator.
        Locale.setDefault(Locale.ENGLISH);                  
    }   
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 1) { //
            usage("ParticleSwarmOptimization");
            return;
        }

        Configuration config;
        try {
            config = new Configuration(args[0]);
        } catch (NoSuchFieldException | IOException e) {
            e.printStackTrace();
            return;
        }

        /* Create the factory */
        FitnessFunctionFactory ffFactory = FitnessFunctionFactory.getInstance();

        /* Factory creates the product - fitness function */
        FitnessFunction ff = ffFactory.createFitnessFunction(config.problem);

        new Solver(ff, config.maxIterations, config.dimension, config.epsilon,
                config.inertiaWeightStart,
                config.inertiaWeightEnd, config.connections, config.c1,
                config.c2, config.weightLimit, config.volumeLimit, 
                config.knapsackInputFile).solve();
    }

    public static void usage(String progname) {
        System.out.println("Usage: " + progname + " CONFIG_FILE");
        System.out.println("Solves problem using particle swarm optimalization.");
    }
}
