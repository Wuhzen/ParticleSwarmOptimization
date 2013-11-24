package pso.knapsack;

import pso.KnapsackProblem;
import pso.Particle;
import pso.Problem;
import pso.Solver;

public class KnapsackSolver extends Solver {
    protected double weightLimit;
    protected double volumeLimit;

	public KnapsackSolver(KnapsackProblem problem, int maxIterations, int dimension,
			double epsilon, double inertiaWeightStart, double inertiaWeightEnd,
			int connections, double c1, double c2, double weightLimit,
			double volumeLimit, String knapsackInputFile) {
		super(problem, maxIterations, dimension, epsilon, inertiaWeightStart,
				inertiaWeightEnd, connections, c1, c2);

		problem.setDimension(dimension);

		boolean considerVolume = (volumeLimit == -1) ? false : true;
		problem.parsePackagesFile(knapsackInputFile,
				considerVolume);

		problem.setWeightLimit(weightLimit);
		problem.setMaxvalue();
		
		int particleCount = 10 + (int) (10 * Math.sqrt(dimension));
		particles.ensureCapacity(particleCount);
		for (int i = 0; i < particleCount; i++) {
            particles.add(new Particle(dimension));
        }
        
        // find the best global position and set it to all of them
        updateBestGlobalPosition(connections);
	}

}
