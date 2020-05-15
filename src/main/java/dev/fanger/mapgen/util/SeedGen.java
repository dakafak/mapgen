package dev.fanger.mapgen.util;

public class SeedGen {

    public static short newSeed() {
        return (short) (Math.random() * 32767);
    }

    public static double randomNumber(double x, double y, short z, double max) {
//        return ((Math.abs(x) + Math.abs(y)) * z) % max;
        return Math.random() * max;
    }

}
