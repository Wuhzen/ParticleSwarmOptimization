package pso;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

import pso.Package.PackageAttributes;
import static pso.FitnessFunction.getRandomNumber;

/**
 * This class implements the fitness function evaluating the knapsack problem.
 */
public class KnapsackProblem extends FitnessFunction {

	static {
		/* Registers itself in the factory. */
		FitnessFunctionFactory.registerFitnessFunction("knapsack",
				new KnapsackProblem());
	}
	private static final double clampMin = CommonConstants.knapsackProblemClampVelocityMin;
	private static final double clampMax = CommonConstants.knapsackProblemClampVelocityMax;
	private ArrayList<Package> packages;
	private double weightLimit;
	private double maxValue;
	private double dimension;

	public void setDimension(double dimension) {
		this.dimension = dimension;
	}

	public KnapsackProblem() {
		super(CommonConstants.knapsackProblemParticlePositionMin,
				CommonConstants.knapsackProblemParticlePositionMax, clampMin,
				clampMax);
		packages = new ArrayList<>();
	}

	public void setWeightLimit(double weightLimit) {
		this.weightLimit = weightLimit;
	}

	/**
	 * Counts the total value of all packages and saves it
	 */
	public void setMaxvalue() {
		maxValue = 0.0;
		for (Package p : packages) {
			maxValue += p.value;
		}
	}

	/**
	 * Implementation of fitness function.
	 * 
	 * @param vector
	 *            particle's current position
	 * @return fitness value
	 */
	@Override
	public double get(ArrayList<Double> vector) {
		double fitness;

		if ((weightLimit - knapsackQuality(vector,
				Package.PackageAttributes.WEIGHT)) < 0.0) {
			//System.out.println("penalty = " + getPenalty(vector));
			//fitness = maxValue
			//		- knapsackQuality(vector, Package.PackageAttributes.VALUE) + getPenalty(vector);
			fitness = Double.MAX_VALUE; 
		} else {
			fitness = maxValue
					- knapsackQuality(vector, Package.PackageAttributes.VALUE);
		}
		//System.out.println("Fitness = " + fitness);

		return fitness;
	}
	
	private double getPenalty(ArrayList<Double> vector) {
		double sumCX = 0.0;
		double sumAX = 0.0;
		for (int i = 0; i < vector.size(); i++) {
			sumAX += packages.get(i).get(PackageAttributes.WEIGHT) * vector.get(i);
		}
		
		return sumAX;
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
	 * @param path
	 *            Path to the file
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

			for (int i = 0; i < dimension; i++) {
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
		} catch (NoSuchElementException e) {
			System.err
					.println("ERROR: Not enough packages specification in file "
							+ path);
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
		int minFitPackages = (int) (weightLimit / maxPackageWeight);

		ArrayList<Double> position = new ArrayList<>(dimension);
		for (int i = 0; i < dimension; i++) {
			position.add(0.0);
		}

		for (int i = 0; (i < dimension) && (i < minFitPackages); i++) {
			int index = (int) getRandomNumber(0, dimension - Double.MIN_VALUE);
			if (position.get(index) != 0.0) {
				//i--;
				continue;
			}
			position.set(index, 1.0);
		}
		

		return position;
	}

	@Override
	public ArrayList<Double> clampPosition(ArrayList<Double> position,
			ArrayList<Double> velocity) {
		double newPos;

		for (int i = 0; i < position.size(); i++) {
			newPos = (getRandomNumber(0.0, 1.0) < sigmoid(velocity.get(i))) ? complement(position.get(i))
					: position.get(i);
			position.set(i, newPos);
		}

		return position;
	}
	
	private double complement(double arg) {
		return (arg == 0.0) ? 1.0 : 0.0;
	}

	@Override
	public ArrayList<Double> clampVelocity(ArrayList<Double> velocity) {
		// int sign;

		for (int i = 0; i < velocity.size(); i++) {
			if (velocity.get(i) < clampMin) {
				velocity.set(i, clampMin);
			} else if (velocity.get(i) > clampMax) {
				velocity.set(i, clampMax);
			}

			// Map to range <-1, 1> using sigmoid function
			// sign = (velocity.get(i) > 0.0) ? 1 : -1;
			// velocity.set(i, sign * sigmoid(velocity.get(i)));
			// velocity.set(i, sigmoid(velocity.get(i)));
		}

		return velocity;
	}

	/**
	 * Implements sigmoid function
	 * 
	 * @param arg
	 * @return
	 */
	private double sigmoid(double arg) {
		return 1 / (1 + Math.exp(-arg));
	}

	/**
	 * Counts current knapsack weight.
	 * 
	 * @param position
	 * @return
	 */
	public double knapsackQuality(ArrayList<Double> position,
			Package.PackageAttributes a) {
		double sum = 0.0;
		for (int i = 0; i < position.size(); i++) {
			sum += position.get(i) * packages.get(i).get(a);
		}
		return sum;
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
