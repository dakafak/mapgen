package dev.fanger.mapgen.generation;

import org.junit.Test;

import static dev.fanger.mapgen.generation.DiamondSquare.printHeightArray;

public class DiamondSquareTest {

    @Test
    public void testDiamondSquare() {
        printHeightArray(DiamondSquare.getHeightMapWithQuadrants(17, 20, 50, 10, 80, 0, 100, (short) 345));
    }

}
