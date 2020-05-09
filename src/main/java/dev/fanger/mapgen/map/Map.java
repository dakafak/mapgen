package dev.fanger.mapgen.map;

import dev.fanger.mapgen.config.MapConfig;
import dev.fanger.mapgen.config.RegionConfig;
import dev.fanger.mapgen.generation.DiamondSquare;
import dev.fanger.mapgen.util.ChunkGrid;

public class Map {

    private int chunkSize;
    private ChunkGrid chunkGrid;
    private MapConfig mapConfig;

    //TODO this should be generated ahead of time and passed into Map(...)
    private long generationSeed = 50_75_20_90L;// Q1(top right), Q2(top left), Q3(bottom left), Q4(bottom right)

    /**
     * Values chunkSize works with
     * 2D square array of width and height 2n + 1
     * 5, 9, 17
     *
     * @param chunkSize
     * @param mapConfig
     */
    public Map(int chunkSize, MapConfig mapConfig) {
        this.chunkGrid = new ChunkGrid();
        this.chunkSize = chunkSize;
        //TODO might have a problem with non-odd chunk sizes
        this.mapConfig = mapConfig;

        generateChunk(0,0);
    }

    // chunk grid x and y - consider changing this
    public void generateChunk(int x, int y) {
        if(getChunk(x, y) != null) {
            return;
        }

        RegionConfig randomRegionConfig = mapConfig.getRandomRegionConfig();
        Chunk newChunk = new Chunk(x, y, chunkSize, randomRegionConfig);

        // get tile height map
        //TODO check if this chunk has any neighbors. It should use the seed values as a backup for corners that do not connect
        double[][] newChunkHeightMap = DiamondSquare.getHeightMapWithQuadrants(chunkSize, 51, 60, 51, 40);
        newChunk.generate(newChunkHeightMap);
        chunkGrid.setChunk(x, y, newChunk);
    }

    public Chunk getChunk(double gameX, double gameY) {
        return chunkGrid.getChunk((int) Math.floor(gameX / chunkSize), (int) Math.floor(gameY / chunkSize));
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
