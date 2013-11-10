package pso;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {
	public String problem;
	public int dimension;
	public int maxIterations;
	public double epsilon;
	public int connections;
	public double inertiaWeightStart;
	public double inertiaWeightEnd;
	public double c1;
	public double c2;
	private Properties defaultProps;

	public Configuration(String path) throws IOException, NoSuchFieldException {
		defaultProps = new Properties();
		FileInputStream in = new FileInputStream(path);
		defaultProps.load(in);
		in.close();

		setValues(defaultProps);
	}

	private void setValues(Properties defaultProps) throws NoSuchFieldException {
		problem = parseString("problem");
		dimension = parseInt("dimension");		
		maxIterations = parseInt("max_iterations");
		epsilon = parseDouble("epsilon");
		connections = parseInt("connections");
		inertiaWeightStart = parseDouble("inertia_weight_start");
		inertiaWeightEnd = parseDouble("inertia_weight_end");
		c1 = parseDouble("c1");
		c2 = parseDouble("c2");
	}

	private String parseString(String key) throws NoSuchFieldException {
		String value = defaultProps.getProperty(key);
		if (value == null)
			throw new NoSuchFieldException(key);
		return value;
	}

	private int parseInt(String key) throws NoSuchFieldException {
		String value = defaultProps.getProperty(key);
		if (value == null)
			throw new NoSuchFieldException(key);
		return Integer.parseInt(value);
	}

	private double parseDouble(String key) throws NoSuchFieldException {
		String value = defaultProps.getProperty(key);
		if (value == null)
			throw new NoSuchFieldException(key);
		return Double.parseDouble(value);
	}
}
