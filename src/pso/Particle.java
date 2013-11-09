package pso;

import java.util.ArrayList;

public class Particle {

    private ArrayList<Double> position = new ArrayList<>();
    private ArrayList<Double> velocity = new ArrayList<>();
    private ArrayList<Double> bestParticlePosition = new ArrayList<>();
    private static ArrayList<Double> bestGlobalPosition = new ArrayList<>();
    private static double c1;
    private static double c2;
    private static double w;

    public Particle(double c1, double c2, double w) {
        this.c1 = c1;
        this.c2 = c2;
        this.w = w;
    }

    public static ArrayList<Double> getBestGlobalPosition() {
        return bestGlobalPosition;
    }

    public static double getC1() {
        return c1;
    }

    public static double getC2() {
        return c2;
    }

    public static double getW() {
        return w;
    }

    public static void setW(double w) {
        Particle.w = w;
    }
    
    /**
     * position(t+1) = position(t) + velocity(t+1).
     * Be sure that you called updateVelocity first!
     */
    public void updatePosition() {
    	position = sumLists(position, velocity);
    }
    
    /**
     * velocity(t+1) = velocity(t) + c1 * r1 * (bestPosition(t) - position(t)) + c2 * r2 * (bestGlobalPosition(t) - position(t))
     */
    public void updateVelocity() {
    	
    	ArrayList<Double> pTxT = subtractLists(bestParticlePosition, position);
    	ArrayList<Double> gTxT = subtractLists(bestGlobalPosition, position);
    	
    	ArrayList<Double> c1r1pTxT = multiplyList(c1*r1, pTxT);
    	ArrayList<Double> c2r2pTxT = multiplyList(c2*r2, gTxT);
 
    	ArrayList<Double> vTc1r1pTxT = sumLists(velocity, c1r1pTxT);
    	ArrayList<Double> vTc1r1pTxTc2r2pTxT = sumLists(vTc1r1pTxT, c2r2pTxT);
    	
    	velocity = vTc1r1pTxTc2r2pTxT;
    }
    
    public static ArrayList<Double> subtractLists(final ArrayList<Double> a, final ArrayList<Double> b) {
    	ArrayList<Double> retval = new ArrayList<Double>();
    	if (a.size() != b.size())
    		throw new ArrayIndexOutOfBoundsException("");
    	for (int i = 0; i < a.size(); i++) {
    		retval.add(a.get(i) - b.get(i));
    	}
    	return retval;
    }
    
    public static ArrayList<Double> sumLists(final ArrayList<Double> a, final ArrayList<Double> b) {
    	ArrayList<Double> retval = new ArrayList<Double>();
    	if (a.size() != b.size())
    		throw new ArrayIndexOutOfBoundsException("");
    	for (int i = 0; i < a.size(); i++) {
    		retval.add(a.get(i) + b.get(i));
    	}
    	return retval;
    }
    
    public static ArrayList<Double> multiplyList(final double c, final ArrayList<Double> list) {
    	ArrayList<Double> retval = new ArrayList<Double>();
    	for (int i = 0; i < list.size(); i++) {
    		retval.add(c * list.get(i));
    	}
    	return retval;
    }
    
}
