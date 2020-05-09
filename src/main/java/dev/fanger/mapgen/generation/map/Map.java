package dev.fanger.mapgen.generation.map;

import dev.fanger.mapgen.generation.map.util.ChunkGrid;

public class Map {

    private int chunkWidth;
    private int chunkHeight;
    private ChunkGrid chunkGrid;

    public Map(int chunkSize) {
        this.chunkGrid = new ChunkGrid();
        this.chunkWidth = chunkSize;
        this.chunkHeight = chunkSize;
    }

    public Chunk getChunkFromCoordinates(int x, int y) {
        return null;
    }

    public Tile getTileFromCoordinates(int x, int y) {
        return null;
    }

    /**
     * Returns true if a chunk within the given distance is null
     *
     * @param x
     * @param y
     * @param distance
     * @return
     */
    public boolean nearbyChunkNeedsGeneration(int x, int y, double distance) {
        return false;
    }

}
