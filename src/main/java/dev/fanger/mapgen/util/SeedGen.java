package dev.fanger.mapgen.util;

import org.apache.commons.rng.core.source64.XorShift1024StarPhi;

import java.util.Objects;

public class SeedGen {

    public static long newSeed() {
        return (long) (Math.random() * Integer.MAX_VALUE);
    }

    /**
     * Uses a new instance of XorShift1024 each time because the seed needs to be dependent on x/y location
     * This random number should be used for generation only because many new instances will be slow for other various
     * random number gen.
     *
     * @param x
     * @param y
     * @param seed
     * @param max
     * @return
     */
    public static double randomNumberRefreshSeed(double x, double y, long seed, double max) {
        SeedPair seedPair = new SeedPair(x, y, seed);
        XorShift1024StarPhi xorShift1024StarPhi = new XorShift1024StarPhi(new long[]{seedPair.hashCode()});
        return xorShift1024StarPhi.nextDouble() * max;
    }

    private static class SeedPair {

        private double x;
        private double y;
        private long seed;

        public SeedPair(double x, double y, long seed) {
            this.x = x;
            this.y = y;
            this.seed = seed;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SeedPair seedPair = (SeedPair) o;
            return Double.compare(seedPair.x, x) == 0 &&
                    Double.compare(seedPair.y, y) == 0 &&
                    seed == seedPair.seed;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, seed);
        }
    }
}
