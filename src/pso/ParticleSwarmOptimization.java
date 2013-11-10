package pso;

import java.io.IOException;
import java.util.ArrayList;

public class ParticleSwarmOptimization {

	/**
	 * @param args
	 *            the command line arguments
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

		FitnessFunction ff = new FitnessFunction() {
			@Override
			public double get(ArrayList<Double> vector) {
				double sum = 0;
				for (Double value : vector) {
					sum += value * value;
				}
				return sum;
			}
		};

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
