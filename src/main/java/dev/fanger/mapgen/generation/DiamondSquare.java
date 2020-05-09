package dev.fanger.mapgen.generation;

import java.util.regex.Pattern;

public class DiamondSquare {
    // Use this to fill in tile values between chunk values

    /**
     * Values this works with
     * 2D square array of width and height 2n + 1
     * 5, 9, 17
     *
     * @param gridSize
     * @param q1Height
     * @param q2Height
     * @param q3Height
     * @param q4Height
     * @return
     */
    public static double[][] getHeightMapWithQuadrants(int gridSize, int q1Height, int q2Height, int q3Height, int q4Height) {
        double[][] heightMap = new double[gridSize][gridSize];
        for(int y = 0; y < heightMap.length; y++) {
            for(int x = 0; x < heightMap[0].length; x++) {
                heightMap[y][x] = -1;
            }
        }

        heightMap[0][gridSize - 1] = q1Height;
        heightMap[0][0] = q2Height;
        heightMap[gridSize - 1][0] = q3Height;
        heightMap[gridSize - 1][gridSize - 1] = q4Height;

        performDiamondAndSquare(heightMap, 0, 0, heightMap.length);

        return heightMap;
    }

    private static void performDiamondAndSquare(double[][] heightMap, int x, int y, int size) {
        int distanceBetweenPoints = size - 1;// If there are 5 points, add (size - 1) to get from the first to last point

        // Only run diamond if there is a gap between points
        if(distanceBetweenPoints > 1) {
            int halfPointDistance = distanceBetweenPoints / 2;
            int diamondMiddleX = x + halfPointDistance;
            int diamondMiddleY = y + halfPointDistance;
            double newPointHeight = (
                    heightMap[y][x]
                    + heightMap[y][x + distanceBetweenPoints]
                    + heightMap[y + distanceBetweenPoints][x]
                    + heightMap[y + distanceBetweenPoints][x + distanceBetweenPoints]
            ) / 4;

            heightMap[diamondMiddleY][diamondMiddleX] = newPointHeight;

            // Add square Values
            double squareHeight = newPointHeight;
            heightMap[y][x + halfPointDistance] = squareHeight;
            heightMap[y + halfPointDistance][x] = squareHeight;
            heightMap[y + halfPointDistance][x + distanceBetweenPoints] = squareHeight;
            heightMap[y + distanceBetweenPoints][x + halfPointDistance] = squareHeight;

            // Re-run with 4 new pieces
            //TODO need to adjust this. It seems to fill out an entire quadrant before the others
            performDiamondAndSquare(heightMap, x, y, halfPointDistance + 1);
            performDiamondAndSquare(heightMap, x + halfPointDistance, y, halfPointDistance + 1);
            performDiamondAndSquare(heightMap, x, y + halfPointDistance, halfPointDistance + 1);
            performDiamondAndSquare(heightMap, x + halfPointDistance, y + halfPointDistance, halfPointDistance + 1);
        }
    }

    public static void printHeightArray(double[][] heightArray) {
        for(int y = 0; y < heightArray.length; y++) {
            String lineData = "";

            for(int x = 0; x < heightArray[0].length; x++) {
                lineData += (int)heightArray[y][x];
                if(x < heightArray[0].length - 1) {
                    lineData += ",\t";
                }
            }

            System.out.println(lineData + System.lineSeparator());
        }
        System.out.println("------------------------------------------------------------");
    }

}
