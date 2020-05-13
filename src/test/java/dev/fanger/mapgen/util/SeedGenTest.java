package dev.fanger.mapgen.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SeedGenTest {

    @Test
    public void testRandomNumber() {
        assertEquals(0, SeedGen.randomNumber(1, 1, (short) 5, 5), .001);

        assertEquals(1, SeedGen.randomNumber(5, -3, (short) 5, 3), .001);
    }

}
