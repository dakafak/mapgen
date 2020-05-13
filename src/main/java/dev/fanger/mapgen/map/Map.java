package dev.fanger.mapgen.map;

import dev.fanger.mapgen.config.MapConfig;
import dev.fanger.mapgen.config.RegionConfig;
import dev.fanger.mapgen.generation.DiamondSquare;
import dev.fanger.mapgen.util.ChunkGrid;
import dev.fanger.mapgen.util.SeedGen;

public class Map {

    private int chunkSize;
    private ChunkGrid chunkGrid;
    private MapConfig mapConfig;
    private short seed;

    /**
     * Values chunkSize works with
     * 2D square array of width and height 2n + 1
     * 5, 9, 17
     *
     * @param chunkSize
     * @param mapConfig
     */
    public Map(int chunkSize, MapConfig mapConfig, short seed) {
        this.chunkGrid = new ChunkGrid();
        this.chunkSize = chunkSize;
        //TODO might have a problem with non-odd chunk sizes
        this.mapConfig = mapConfig;
        this.seed = seed;

        generateChunk(0,0);
    }

    // chunk grid chunkX and chunkY - consider changing this
    public void generateChunk(int chunkX, int chunkY) {
        if(getChunk(chunkX, chunkY) != null) {
            return;
        }

        RegionConfig randomRegionConfig = mapConfig.getRandomRegionConfig(chunkX, chunkY, seed);
        Chunk newChunk = new Chunk(chunkX, chunkY, chunkSize, randomRegionConfig);

        // Quadrant heights
        double newQ1Height = getQ1Height(chunkX, chunkY, SeedGen.randomNumber(chunkX + 1, chunkY - 1, seed, 100));
        double newQ2Height = getQ2Height(chunkX, chunkY, SeedGen.randomNumber(chunkX - 1, chunkY - 1, seed, 100));
        double newQ3Height = getQ3Height(chunkX, chunkY, SeedGen.randomNumber(chunkX - 1, chunkY + 1, seed, 100));
        double newQ4Height = getQ4Height(chunkX, chunkY, SeedGen.randomNumber(chunkX + 1, chunkY + 1, seed, 100));

        // get tile height map
        //TODO check if this chunk has any neighbors. It should use the seed values as a backup for corners that do not connect
        double[][] newChunkHeightMap = DiamondSquare.getHeightMapWithQuadrants(chunkSize, newQ1Height, newQ2Height, newQ3Height, newQ4Height, seed);
        newChunk.generate(newChunkHeightMap);
        chunkGrid.setChunk(chunkX, chunkY, newChunk);
    }

    private double getQ1Height(int chunkX, int chunkY, double random) {
        if(chunkGrid.getChunk(chunkX, chunkY - 1) != null) {
            return chunkGrid.getChunk(chunkX, chunkY - 1).getQ4Height();
        } else if(chunkGrid.getChunk(chunkX + 1, chunkY - 1) != null) {
            return chunkGrid.getChunk(chunkX + 1, chunkY - 1).getQ3Height();
        } else if(chunkGrid.getChunk(chunkX + 1, chunkY) != null) {
            return chunkGrid.getChunk(chunkX + 1, chunkY).getQ2Height();
        } else {
            return random;
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


    public Chunk getChunk(double gameX, double gameY) {
        return chunkGrid.getChunk((int) Math.floor(gameX / chunkSize), (int) Math.floor(gameY / chunkSize));
    }

    public double getGameX(int chunkX) {
        return chunkX * chunkSize;
    }

    public double getGameY(int chunkY) {
        return chunkY * chunkSize;
    }

    public Tile getTile(double gameX, double gameY) {
        Chunk chunk = getChunk(gameX, gameY);
        return chunk.getTile(gameX, gameY);
    }

    // chunk grid x and y - consider changing this
    public Chunk getChunk(int x, int y) {
        return chunkGrid.getChunk(x, y);
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
