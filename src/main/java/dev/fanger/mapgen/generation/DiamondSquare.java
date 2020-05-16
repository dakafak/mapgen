package dev.fanger.mapgen.generation;

import dev.fanger.mapgen.map.location.Point;
import dev.fanger.mapgen.util.SeedGen;

import java.util.LinkedList;
import java.util.Queue;

public class DiamondSquare {
    // Use this to fill in tile values between chunk values

    private static int STARTING_RANDOM_ADD_RANGE = 10;
    private static final double MIN_HEIGHT = 0;
    private static final double MAX_HEIGHT = 100;

    /**
     * Values this works with
     * 2D square array of width and height 2n + 1
     * 5, 9, 17
     * q1/2/3/4Height values are corner values
     * Double arrays of values are copied values from nearby chunks - minus corner values
     *
     * @param gridSize
     * @param seed
     * @Param spawnHeight
     * @param q1Height
     * @param q2Height
     * @param q3Height
     * @param q4Height
     * @param northValues
     * @param eastValues
     * @param southValues
     * @param westValues
     * @return
     */
    public static double[][] getDiamondSquareHeightMap(int gridSize,
                                                       long seed,
                                                       double spawnHeight,
                                                       double q1Height,
                                                       double q2Height,
                                                       double q3Height,
                                                       double q4Height,
                                                       double[] northValues,
                                                       double[] eastValues,
                                                       double[] southValues,
                                                       double[] westValues) {
        // Initialize height map
        double[][] heightMap = new double[gridSize][gridSize];

        // Initialize all heights to -1
        for(int y = 0; y < heightMap.length; y++) {
            for(int x = 0; x < heightMap[0].length; x++) {
                heightMap[y][x] = -1;
            }
        }

        // Set middle spawn height to ensure a valid spawn location for the character
        if(spawnHeight >= 0) {
            int middleOfGrid = (gridSize - 1) / 2;
            heightMap[middleOfGrid][middleOfGrid] = spawnHeight;

            q1Height = spawnHeight + getRandomAddFromRange(0, 0, seed, STARTING_RANDOM_ADD_RANGE);
            q2Height = spawnHeight + getRandomAddFromRange(0, 0, seed, STARTING_RANDOM_ADD_RANGE);
            q3Height = spawnHeight + getRandomAddFromRange(0, 0, seed, STARTING_RANDOM_ADD_RANGE);
            q4Height = spawnHeight + getRandomAddFromRange(0, 0, seed, STARTING_RANDOM_ADD_RANGE);
        }

        // Initialize corners with quadrant values
        heightMap[0][gridSize - 1] = q1Height;
        heightMap[0][0] = q2Height;
        heightMap[gridSize - 1][0] = q3Height;
        heightMap[gridSize - 1][gridSize - 1] = q4Height;

        // Remember, this array does not include corners so it's length is (gridSize - 2)
        int edgeArrayLength = gridSize - 2;

        // Initialize edges
        for(int i = 0; i < edgeArrayLength; i++) {
            if(northValues != null) {
                heightMap[0][i + 1] = northValues[i];
            }

            if(eastValues != null) {
                heightMap[i + 1][gridSize - 1] = eastValues[i];
            }

            if(southValues != null) {
                heightMap[gridSize - 1][i + 1] = southValues[i];
            }

            if(westValues != null) {
                heightMap[i + 1][0] = westValues[i];
            }
        }

        return getDiamondSquareHeightMap(heightMap, gridSize, seed);
    }

    protected static double[][] getDiamondSquareHeightMap(double[][] initializedHeightMap, int gridSize, long seed) {
        // Set a starting random add range
        double randomAddRange = STARTING_RANDOM_ADD_RANGE;

        // Initialize list of points to perform diamond square on
        Queue<DiamondSquarePoint> pointsToDiamondSquare = new LinkedList<>();
        pointsToDiamondSquare.add(new DiamondSquarePoint(0, 0, gridSize));

        while(!pointsToDiamondSquare.isEmpty()) {
            Queue<DiamondSquarePoint> diamondPointsToExecute = new LinkedList<>();
            diamondPointsToExecute.addAll(pointsToDiamondSquare);
            pointsToDiamondSquare.clear();

            Queue<DiamondSquarePoint> squareStepQueue = new LinkedList<>();

            // Run through all diamond steps queued up
            while(!diamondPointsToExecute.isEmpty()) {
                DiamondSquarePoint currentDiamondSquarePoint = diamondPointsToExecute.poll();

                // Perform diamond step
                setAverageFromDiamondSquarePoint(initializedHeightMap, seed, randomAddRange, currentDiamondSquarePoint);

                // Add this diamond square location to the square location queue
                squareStepQueue.add(currentDiamondSquarePoint);

                // Add new diamond square points to queue -- only if the current square size is greater than 3
                if (currentDiamondSquarePoint.getSquareSize() > 3) {
                    pointsToDiamondSquare.add(new DiamondSquarePoint(currentDiamondSquarePoint.getX(), currentDiamondSquarePoint.getY(), currentDiamondSquarePoint.getHalfDistance() + 1));
                    pointsToDiamondSquare.add(new DiamondSquarePoint(currentDiamondSquarePoint.getX() + currentDiamondSquarePoint.getHalfDistance(), currentDiamondSquarePoint.getY(), currentDiamondSquarePoint.getHalfDistance() + 1));
                    pointsToDiamondSquare.add(new DiamondSquarePoint(currentDiamondSquarePoint.getX(), currentDiamondSquarePoint.getY() + currentDiamondSquarePoint.getHalfDistance(), currentDiamondSquarePoint.getHalfDistance() + 1));
                    pointsToDiamondSquare.add(new DiamondSquarePoint(currentDiamondSquarePoint.getX() + currentDiamondSquarePoint.getHalfDistance(), currentDiamondSquarePoint.getY() + currentDiamondSquarePoint.getHalfDistance(), currentDiamondSquarePoint.getHalfDistance() + 1));
                }
            }

            // Run through all square steps
            while(!squareStepQueue.isEmpty()) {
                DiamondSquarePoint currentDiamondSquarePoint = squareStepQueue.poll();
                Point diamondPoint = currentDiamondSquarePoint.getDiamondPoint();

                // Perform square step for each location surrounding the new diamond location
                setSquarePointHeight(initializedHeightMap, seed, randomAddRange, diamondPoint.getX(), diamondPoint.getY() - currentDiamondSquarePoint.getHalfDistance(), currentDiamondSquarePoint.getHalfDistance());
                setSquarePointHeight(initializedHeightMap, seed, randomAddRange, diamondPoint.getX() - currentDiamondSquarePoint.getHalfDistance(), diamondPoint.getY(), currentDiamondSquarePoint.getHalfDistance());
                setSquarePointHeight(initializedHeightMap, seed, randomAddRange, diamondPoint.getX(), diamondPoint.getY() + currentDiamondSquarePoint.getHalfDistance(), currentDiamondSquarePoint.getHalfDistance());
                setSquarePointHeight(initializedHeightMap, seed, randomAddRange, diamondPoint.getX() + currentDiamondSquarePoint.getHalfDistance(), diamondPoint.getY(), currentDiamondSquarePoint.getHalfDistance());
            }

            randomAddRange = getNextScaledRandomRange(randomAddRange);
        }

        // Return completed height map
        return initializedHeightMap;
    }

    protected static void setAverageFromDiamondSquarePoint(double[][] heightMap, long seed, double randomAddRange, DiamondSquarePoint diamondSquarePoint) {
        double diamondHeight = (heightMap[diamondSquarePoint.getY()][diamondSquarePoint.getX()]
                + heightMap[diamondSquarePoint.getY() + diamondSquarePoint.getDistanceBetweenPoints()][diamondSquarePoint.getX()]
                + heightMap[diamondSquarePoint.getY()][diamondSquarePoint.getX() + diamondSquarePoint.getDistanceBetweenPoints()]
                + heightMap[diamondSquarePoint.getY() + diamondSquarePoint.getDistanceBetweenPoints()][diamondSquarePoint.getX() + diamondSquarePoint.getDistanceBetweenPoints()])
                / 4.0;

        Point diamondPoint = diamondSquarePoint.getDiamondPoint();
        diamondHeight += getRandomAddFromRange(diamondPoint.getX(), diamondPoint.getY(), seed, randomAddRange);

        setValueOnHeightMap(heightMap, diamondPoint.getX(), diamondPoint.getY(), diamondHeight);
    }

    protected static void setSquarePointHeight(double[][] heightMap, long seed, double randomRange, int x, int y, int distanceToDiamondCorners) {
        int numberCorners = 0;
        double totalHeight = 0;

        if(pointOnGrid(heightMap, x, y - distanceToDiamondCorners)) {
            double height = heightMap[y - distanceToDiamondCorners][x];
            numberCorners++;
            totalHeight += height;
        }

        if(pointOnGrid(heightMap, x - distanceToDiamondCorners, y)) {
            double height = heightMap[y][x - distanceToDiamondCorners];
            numberCorners++;
            totalHeight += height;
        }

        if(pointOnGrid(heightMap, x, y + distanceToDiamondCorners)) {
            double height = heightMap[y + distanceToDiamondCorners][x];
            numberCorners++;
            totalHeight += height;
        }

        if(pointOnGrid(heightMap, x + distanceToDiamondCorners, y)) {
            double height = heightMap[y][x + distanceToDiamondCorners];
            numberCorners++;
            totalHeight += height;
        }

        double newSquareHeight = (totalHeight / numberCorners) + getRandomAddFromRange(x, y, seed, randomRange);

        setValueOnHeightMap(heightMap, x, y, newSquareHeight);
    }

    private static void setValueOnHeightMap(double[][] heightMap, int x, int y, double height) {
        if(heightMap[y][x] == -1) {
            if(height < MIN_HEIGHT) {
                height = MIN_HEIGHT;
            } else if(height > MAX_HEIGHT) {
                height = MAX_HEIGHT;
            }

            heightMap[y][x] = height;
        }
    }

    private static boolean pointOnGrid(double[][] heightMap, int x, int y) {
        return y >= 0 && x >= 0 && y < heightMap.length && x < heightMap[0].length;
    }

    private static double getNextScaledRandomRange(double originalRange) {
        return originalRange / 2;
    }

    private static double getRandomAddFromRange(double x, double y, long seed, double range) {
        return SeedGen.randomNumberRefreshSeed(x, y, seed, range) - (range / 2);
//        return SeedGen.randomNumberRefreshSeed(x, y, seed, range);
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
