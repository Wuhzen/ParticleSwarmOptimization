package pso;

import java.util.ArrayList;

public class Solver {

	private FitnessFunction fitness;
	private int maxIterations;
	private int dimension;
	private double epsilon;
	private int rounds;
	private double inertiaWeightEnd;
	private double inertiaWeightStart;
	private int connections;
	private ArrayList<Particle> particles = new ArrayList<Particle>();
	private double c2;
	private double c1;

	public Solver(FitnessFunction fitness, int maxIterations, int dimension,
			double epsilon, int rounds, double inertiaWeightStart,
			double inertiaWeightEnd, int connections, double c1, double c2) {
		this.fitness = fitness;
		this.maxIterations = maxIterations;
		this.dimension = dimension;
		this.epsilon = epsilon;
		this.rounds = rounds;
		this.inertiaWeightStart = inertiaWeightStart;
		this.inertiaWeightEnd = inertiaWeightEnd;
		this.connections = connections;
		this.c1 = c1;
		this.c2 = c2;
		
		for (int i = 0; i < 10 + 2 * Math.sqrt(dimension); i++)
			particles.add(new Particle(0, 1, inertiaWeightStart));

	}

	public void solve() {
		for (int i = 0; i < maxIterations; i++) {

		}
	}
}
