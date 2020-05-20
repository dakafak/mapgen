package dev.fanger.mapgen.map;

import dev.fanger.mapgen.config.MapConfig;
import dev.fanger.mapgen.generation.DiamondSquare;
import dev.fanger.mapgen.map.location.Direction;
import dev.fanger.mapgen.util.ChunkGrid;
import dev.fanger.mapgen.util.SeedGen;

public class GameMap {

    private int chunkSize;
    private ChunkGrid chunkGrid;
    private MapConfig mapConfig;
    private long seed;

    //TODO need constructor to build from saved map

    /**
     * Chunk size will equal (2^chunkSizeMagnitude + 1)
     *
     * @param chunkSizeMagnitude
     * @param mapConfig
     */
    public GameMap(int chunkSizeMagnitude, MapConfig mapConfig, long seed) {
        this.chunkGrid = new ChunkGrid();
        this.chunkSize = (int) Math.pow(2, chunkSizeMagnitude) + 1;
        this.mapConfig = mapConfig;
        this.seed = seed;

        generateChunk(0,0);
    }

    // chunk grid chunkX and chunkY - consider changing this
    public void generateChunk(int chunkX, int chunkY) {
        if(getChunk(chunkX, chunkY) != null) {
            return;
        }

        Chunk newChunk = new Chunk(chunkX, chunkY, chunkSize);

        double q1Height;
        double q2Height;
        double q3Height;
        double q4Height;

        if(chunkX == 0 && chunkY == 0) {
            double startingPointRange = 10;
            q1Height = mapConfig.getSpawnHeight() + (SeedGen.randomNumberRefreshSeed(chunkX + 1, chunkY - 1, seed, startingPointRange) - (startingPointRange / 2));
            q2Height = mapConfig.getSpawnHeight() + (SeedGen.randomNumberRefreshSeed(chunkX - 1, chunkY - 1, seed, startingPointRange) - (startingPointRange / 2));
            q3Height = mapConfig.getSpawnHeight() + (SeedGen.randomNumberRefreshSeed(chunkX - 1, chunkY + 1, seed, startingPointRange) - (startingPointRange / 2));
            q4Height = mapConfig.getSpawnHeight() + (SeedGen.randomNumberRefreshSeed(chunkX + 1, chunkY + 1, seed, startingPointRange) - (startingPointRange / 2));
        } else {
            q1Height = getQ1Height(chunkX, chunkY, SeedGen.randomNumberRefreshSeed(chunkX + 1, chunkY - 1, seed, 100));
            q2Height = getQ2Height(chunkX, chunkY, SeedGen.randomNumberRefreshSeed(chunkX - 1, chunkY - 1, seed, 100));
            q3Height = getQ3Height(chunkX, chunkY, SeedGen.randomNumberRefreshSeed(chunkX - 1, chunkY + 1, seed, 100));
            q4Height = getQ4Height(chunkX, chunkY, SeedGen.randomNumberRefreshSeed(chunkX + 1, chunkY + 1, seed, 100));
        }

        // get tile height map with corner quadrant heights and nearby edges from any surrounding chunks -- minus corners as those are above
        double[][] newChunkHeightMap = DiamondSquare.getDiamondSquareHeightMap(
                chunkSize,
                seed,
                q1Height,
                q2Height,
                q3Height,
                q4Height,
                getHeightArray(Direction.NORTH, chunkX, chunkY),
                getHeightArray(Direction.EAST, chunkX, chunkY),
                getHeightArray(Direction.SOUTH, chunkX, chunkY),
                getHeightArray(Direction.WEST, chunkX, chunkY));

        newChunk.generate(newChunkHeightMap, seed, mapConfig);
        chunkGrid.setChunk(chunkX, chunkY, newChunk);
    }

    private double getQ1Height(int chunkX, int chunkY, double maxRandom) {
        if(chunkGrid.getChunk(chunkX, chunkY - 1) != null) {
            return chunkGrid.getChunk(chunkX, chunkY - 1).getQ4Height();
        } else if(chunkGrid.getChunk(chunkX + 1, chunkY - 1) != null) {
            return chunkGrid.getChunk(chunkX + 1, chunkY - 1).getQ3Height();
        } else if(chunkGrid.getChunk(chunkX + 1, chunkY) != null) {
            return chunkGrid.getChunk(chunkX + 1, chunkY).getQ2Height();
        } else {
            return maxRandom;
        }
    }

    private double getQ2Height(int chunkX, int chunkY, double random) {
        if(chunkGrid.getChunk(chunkX, chunkY - 1) != null) {
            return chunkGrid.getChunk(chunkX, chunkY - 1).getQ3Height();
        } else if(chunkGrid.getChunk(chunkX - 1, chunkY - 1) != null) {
            return chunkGrid.getChunk(chunkX - 1, chunkY - 1).getQ4Height();
        } else if(chunkGrid.getChunk(chunkX - 1, chunkY) != null) {
            return chunkGrid.getChunk(chunkX - 1, chunkY).getQ1Height();
        } else {
            return random;
        }
    }

    private double getQ3Height(int chunkX, int chunkY, double random) {
        if(chunkGrid.getChunk(chunkX - 1, chunkY) != null) {
            return chunkGrid.getChunk(chunkX - 1, chunkY).getQ4Height();
        } else if(chunkGrid.getChunk(chunkX - 1, chunkY + 1) != null) {
            return chunkGrid.getChunk(chunkX - 1, chunkY + 1).getQ1Height();
        } else if(chunkGrid.getChunk(chunkX, chunkY + 1) != null) {
            return chunkGrid.getChunk(chunkX, chunkY + 1).getQ2Height();
        } else {
            return random;
        }
    }

    private double getQ4Height(int chunkX, int chunkY, double random) {
        if(chunkGrid.getChunk(chunkX, chunkY + 1) != null) {
            return chunkGrid.getChunk(chunkX, chunkY + 1).getQ1Height();
        } else if(chunkGrid.getChunk(chunkX + 1, chunkY + 1) != null) {
            return chunkGrid.getChunk(chunkX + 1, chunkY + 1).getQ2Height();
        } else if(chunkGrid.getChunk(chunkX + 1, chunkY) != null) {
            return chunkGrid.getChunk(chunkX + 1, chunkY).getQ3Height();
        } else {
            return random;
        }
    }

    /**
     * Get an array of edge heights for the new chunk based on the direction of the new chunk
     * This does not include corner values so the size of this array will be {@link #chunkSize} - 2
     * This array will always be ordered left to right and top to bottom
     *
     * @param newChunkX
     * @param newChunkY
     * @return
     */
    private double[] getHeightArray(Direction direction, int newChunkX, int newChunkY) {
        Chunk chunk = null;

        if(direction == Direction.NORTH) {
            chunk = chunkGrid.getChunk(newChunkX, newChunkY - 1);
        } else if(direction == Direction.EAST) {
            chunk = chunkGrid.getChunk(newChunkX + 1, newChunkY);
        } else if(direction == Direction.SOUTH) {
            chunk = chunkGrid.getChunk(newChunkX, newChunkY + 1);
        } else if(direction == Direction.WEST) {
            chunk = chunkGrid.getChunk(newChunkX - 1, newChunkY);
        }

        if(chunk != null) {
            int edgeArraySize = chunkSize - 2;

            // We don't want to include corner values so subtract 2
            double[] heightArray = new double[edgeArraySize];

            // Get values from chunk to fill the new height array
            for(int i = 0; i < edgeArraySize; i++) {
                if(direction == Direction.NORTH) {
                    heightArray[i] = chunk.getTileGrid()[chunkSize - 1][i + 1].getHeight();
                } else if(direction == Direction.EAST) {
                    heightArray[i] = chunk.getTileGrid()[i + 1][0].getHeight();
                } else if(direction == Direction.SOUTH) {
                    heightArray[i] = chunk.getTileGrid()[0][i + 1].getHeight();
                } else if(direction == Direction.WEST) {
                    heightArray[i] = chunk.getTileGrid()[i + 1][chunkSize - 1].getHeight();
                }
            }

            return heightArray;
        }

        return null;
    }

    /**
     * Get a chunk based on the inputted gameX and gameY
     * These values are based on one tile being 1x1
     *
     * @param gameX
     * @param gameY
     * @return
     */
    public Chunk getChunk(double gameX, double gameY) {
        return chunkGrid.getChunk((int) Math.floor(gameX / chunkSize), (int) Math.floor(gameY / chunkSize));
    }

    public double getGameX(int chunkX) {
        return chunkX * chunkSize;
    }

    public double getGameY(int chunkY) {
        return chunkY * chunkSize;
    }

    public double getOffsetFromChunkX(int chunkX, double gameX) {
        return gameX - getGameX(chunkX);
    }

    public double getOffsetFromChunkY(int chunkY, double gameY) {
        return gameY - getGameY(chunkY);
    }

    public Tile getTile(double gameX, double gameY) {
        Chunk chunk = getChunk(gameX, gameY);
        return chunk.getTile(gameX, gameY);
    }

    /**
     * Get a chunk based on the inputted chunkX and chunkY
     * These values are based on chunk location on the chunk grid
     *
     * @param chunkX
     * @param chunkY
     * @return
     */
    public Chunk getChunk(int chunkX, int chunkY) {
        return chunkGrid.getChunk(chunkX, chunkY);
    }

    public ChunkGrid getChunkGrid() {
        return chunkGrid;
    }

    public MapConfig getMapConfig() {
        return mapConfig;
    }

    public int getChunkSize() {
        return chunkSize;
    }

}
