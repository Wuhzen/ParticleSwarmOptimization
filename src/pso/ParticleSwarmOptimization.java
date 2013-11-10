package pso;

import java.io.IOException;
import java.util.ArrayList;
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

        /* Creation of the factory */
        FitnessFunctionFactory ffFactory = FitnessFunctionFactory.getInstance();

        FitnessFunction ff = ffFactory.createFitnessFunction("circle");

        new Solver(ff, config.maxIterations, config.dimension, config.epsilon,
                config.inertiaWeightStart,
                config.inertiaWeightEnd, config.connections, config.c1,
                config.c2).solve();
    }

    public static void usage(String progname) {
        System.out.println("Usage: " + progname + " CONFIG_FILE");
        System.out.println("Solves problem using particle swarm optimalization.");
    }
}
