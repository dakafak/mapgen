package dev.fanger.mapgen.generation;

import dev.fanger.mapgen.util.SeedGen;

public class DiamondSquare {
    // Use this to fill in tile values between chunk values

    private static int minHeight = 0;
    private static int maxHeight = 100;

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
    public static double[][] getHeightMapWithQuadrants(int gridSize, double q1Height, double q2Height, double q3Height, double q4Height, short seed) {
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

        performDiamondAndSquare(heightMap, 0, 0, heightMap.length, seed);

        return heightMap;
    }

    private static void performDiamondAndSquare(double[][] heightMap, int x, int y, int size, short seed) {
        int distanceBetweenPoints = size - 1;// If there are 5 points, add (size - 1) to get from the first to last point

        // Only run diamond if there is a gap between points
        if(distanceBetweenPoints > 1) {
            int halfPointDistance = distanceBetweenPoints / 2;

            int diamondMiddleX = x + halfPointDistance;
            int diamondMiddleY = y + halfPointDistance;
            heightMap[diamondMiddleY][diamondMiddleX] = getAverageDiamondHeightFromPoint(heightMap, x, y, distanceBetweenPoints, seed);

            setAverageSquareHeightFromPoint(heightMap, x + halfPointDistance, y, halfPointDistance, seed);
            setAverageSquareHeightFromPoint(heightMap, x, y + halfPointDistance, halfPointDistance, seed);
            setAverageSquareHeightFromPoint(heightMap, x + distanceBetweenPoints, y + halfPointDistance, halfPointDistance, seed);
            setAverageSquareHeightFromPoint(heightMap, x + halfPointDistance, y + distanceBetweenPoints, halfPointDistance, seed);

            // Re-run with 4 new pieces
            //TODO need to adjust this. It seems to fill out an entire quadrant before the others
            performDiamondAndSquare(heightMap, x, y, halfPointDistance + 1, seed);
            performDiamondAndSquare(heightMap, x + halfPointDistance, y, halfPointDistance + 1, seed);
            performDiamondAndSquare(heightMap, x, y + halfPointDistance, halfPointDistance + 1, seed);
            performDiamondAndSquare(heightMap, x + halfPointDistance, y + halfPointDistance, halfPointDistance + 1, seed);
        }
    }

    private static double getAverageDiamondHeightFromPoint(double[][] heightMap, int x, int y, int distanceBetweenPoints, short seed) {
        double newPointHeight = (
                heightMap[y][x]
                        + heightMap[y][x + distanceBetweenPoints]
                        + heightMap[y + distanceBetweenPoints][x]
                        + heightMap[y + distanceBetweenPoints][x + distanceBetweenPoints]
        ) / 4;
        newPointHeight += (SeedGen.randomNumber(x, y, seed, 10) - 5);

        if(newPointHeight < minHeight) {
            newPointHeight = minHeight;
        } else if(newPointHeight > maxHeight) {
            newPointHeight = maxHeight;
        }

        return newPointHeight;
    }

    private static void setAverageSquareHeightFromPoint(double[][] heightMap, int x, int y, int distanceToDiamondCorners, short seed) {
        int numberCorners = 0;
        double totalHeight = 0;

        if(pointOnGrid(heightMap, x, y - distanceToDiamondCorners)) {
            numberCorners++;
            totalHeight += heightMap[y - distanceToDiamondCorners][x];
        }

        if(pointOnGrid(heightMap, x - distanceToDiamondCorners, y)) {
            numberCorners++;
            totalHeight += heightMap[y][x - distanceToDiamondCorners];
        }

        if(pointOnGrid(heightMap, x, y + distanceToDiamondCorners)) {
            numberCorners++;
            totalHeight += heightMap[y + distanceToDiamondCorners][x];
        }

        if(pointOnGrid(heightMap, x + distanceToDiamondCorners, y)) {
            numberCorners++;
            totalHeight += heightMap[y][x + distanceToDiamondCorners];
        }

        double newSquareHeight = (totalHeight / numberCorners) + (SeedGen.randomNumber(x, y, seed, 10) - 5);

        if(newSquareHeight < minHeight) {
            newSquareHeight = minHeight;
        } else if(newSquareHeight > maxHeight) {
            newSquareHeight = maxHeight;
        }

        heightMap[y][x] = newSquareHeight;
    }

    private static boolean pointOnGrid(double[][] heightMap, int x, int y) {
        return y >= 0 && x >= 0 && y < heightMap.length && x < heightMap[0].length;
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
