package dev.fanger.mapgen.generation;

import org.junit.Before;
import org.junit.Test;

import static dev.fanger.mapgen.generation.DiamondSquare.printHeightArray;

public class DiamondSquareTest {

    double[][] heightMap;

    @Before
    public void setup() {
        heightMap = new double[][]{
                {50, 0,  0,  0,  70},
                {0,  0,  0,  0,  0},
                {0,  0,  0,  0,  0},
                {0,  0,  0,  0,  0},
                {90, 0,  0,  0,  20}
        };
    }

    @Test
    public void testDiamondSquare() {
//        printHeightArray(DiamondSquare.getHeightMapWithQuadrants(17, 20, 50, 10, 80, 0, 100, 345));
        double[][] testGeneratedHeightMap = DiamondSquare.getDiamondSquareHeightMap(5, 345, 70, 50, 90, 20, null, null, null, null);
        printHeightArray(testGeneratedHeightMap);
    }

}
