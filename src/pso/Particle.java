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
}
