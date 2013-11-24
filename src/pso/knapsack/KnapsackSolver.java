package pso.knapsack;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import pso.KnapsackProblem;
import pso.Particle;
import pso.Problem;
import pso.Solver;
import pso.CommonConstants;
import pso.Package.PackageAttributes;

public class KnapsackSolver extends Solver {

	protected double weightLimit;
	protected double volumeLimit;

	public KnapsackSolver(KnapsackProblem problem, int maxIterations,
			int dimension, double epsilon, double inertiaWeightStart,
			double inertiaWeightEnd, int connections, double c1, double c2,
			double weightLimit, double volumeLimit, String knapsackInputFile) {

		super(problem, maxIterations, dimension, epsilon, inertiaWeightStart,
				inertiaWeightEnd, connections, c1, c2);

		problem.setDimension(dimension);
		problem.setWeightLimit(weightLimit);
		problem.setVolumeLimit(volumeLimit);
		problem.parsePackagesFile(knapsackInputFile);

		if (volumeLimit != -1) {
			problem.generateRandomValues(
					CommonConstants.knapsackProblemPackageVolumeMin,
					CommonConstants.knapsackProblemPackageVolumeMax);
		}

		problem.setMaxvalue();

		int particleCount = 10 + (int) (2 * Math.sqrt(dimension));
		// int particleCount = 1000;
		particles.ensureCapacity(particleCount);
		for (int i = 0; i < particleCount; i++) {
			particles.add(new Particle(dimension));
		}

		// find the best global position and set it to all of them
		updateBestGlobalPosition(connections);
	}

	public int solve() throws FileNotFoundException,
			UnsupportedEncodingException {
		int retval = super.solve();

		System.out.println("#value = "
				+ ((KnapsackProblem) problem).knapsackQuality(
						getBestGlobalPosition(), PackageAttributes.VALUE));
		System.out.println("#weight = "
				+ ((KnapsackProblem) problem).knapsackQuality(
						getBestGlobalPosition(), PackageAttributes.WEIGHT));
		return retval;
	}
}
