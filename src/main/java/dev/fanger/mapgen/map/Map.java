package dev.fanger.mapgen.map;

import dev.fanger.mapgen.config.MapConfig;
import dev.fanger.mapgen.util.ChunkGrid;

public class Map {

    private int chunkWidth;
    private int chunkHeight;
    private ChunkGrid chunkGrid;
    private MapConfig mapConfig;

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

    //TODO add methods for handling map objects like moving them? -- MapObject should be very abstract and
    //  it should have implement versions like, resource (rock/tree), unit (player, bandit), building (dock, house)
    //      this, with the solid check, could make a nice way for adding collisions for all objects with each other
    //      so long as I have a good lookup method for finding nearby objects...
    //  ... Probably don't have this here... I should add other tools for game managers. Unit manager, object manager, entity manager, etc.
    //      the managers could be standalone tools that control lists of game objects and handle interactions based on their coordinates

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

    public ChunkGrid getChunkGrid() {
        return chunkGrid;
    }

    public MapConfig getMapConfig() {
        return mapConfig;
    }

}
