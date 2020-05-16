package dev.fanger.mapgen.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SeedGenTest {

    @Test
    public void testRandomNumber() {
        assertEquals(0, SeedGen.randomNumberRefreshSeed(1, 1, 5, 5), .001);

        assertEquals(1, SeedGen.randomNumberRefreshSeed(5, -3, 5, 3), .001);
    }

}
