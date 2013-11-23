
package pso;

/**
 * This class holds the properties of the "package"
 * used in knapsack problem.
 */
public class Package {
    public double value;
    public double weight;
    public double volume;
    
    public enum PackageAttributes {
        VALUE, WEIGHT, VOLUME;
    }
    
    public Package(double value, double weight, double volume) {
        this.value = value;
        this.weight = weight;
        this.volume = volume;
    }        
    
    public double get(PackageAttributes a) {
        switch(a) {
            case VALUE:
                return value;
            case WEIGHT:
                return weight;
            case VOLUME:
                return volume;
            default:
                return 0.0;
        }
    }
}