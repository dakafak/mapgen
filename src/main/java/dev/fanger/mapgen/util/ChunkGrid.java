package dev.fanger.mapgen.util;

import dev.fanger.mapgen.map.Chunk;

public class ChunkGrid {

    private Chunk[][] chunkMap;
    private int zeroX;
    private int zeroY;
    private int growSize;

    /**
     * Initializes with a size of 1x1 and 1 for the growth size
     */
    public ChunkGrid() {
        this(1);
    }

    public ChunkGrid(int growSize) {
        chunkMap = new Chunk[1][1];
        this.zeroX = 0;
        this.zeroY = 0;
        this.growSize = growSize;
    }

    public synchronized void addChunk(int x, int y, Chunk chunk) {
        // TODO There is a more efficient way to grow instead of one growth at a time - consider implementing this

        while(getChunkMapY(y) < 0) {
            growNorth();
        }

        while(getChunkMapY(y) >= getHeight()) {
            growSouth();
        }

        while(getChunkMapX(x) < 0) {
            growWest();
        }

        while(getChunkMapX(x) >= getWidth()) {
            growEast();
        }

        chunkMap[getChunkMapY(y)][getChunkMapX(x)] = chunk;
    }

    public synchronized Chunk getChunk(int x, int y) {
        int chunkMapX = getChunkMapX(x);
        int chunkMapY = getChunkMapY(y);

        if(chunkMapX < 0
                || chunkMapX >= getWidth()
                || chunkMapY < 0
                || chunkMapY >= getHeight()) {
            return null;
        }

        return chunkMap[chunkMapY][chunkMapX];
    }

    public synchronized void growNorth() {
        Chunk[][] newChunkMap = new Chunk[getHeight() + growSize][getWidth()];

        for(int y = 0; y < getHeight(); y++) {
            for(int x = 0; x < getWidth(); x++) {
                newChunkMap[y + growSize][x] = chunkMap[y][x];
            }
        }

        this.chunkMap = newChunkMap;
        zeroY += growSize;
    }

    public synchronized void growEast() {
        Chunk[][] newChunkMap = new Chunk[getHeight()][getWidth() + growSize];

        for(int y = 0; y < getHeight(); y++) {
            for(int x = 0; x < getWidth(); x++) {
                newChunkMap[y][x] = chunkMap[y][x];
            }
        }

        this.chunkMap = newChunkMap;
    }

    public synchronized void growSouth() {
        Chunk[][] newChunkMap = new Chunk[getHeight() + growSize][getWidth()];

        for(int y = 0; y < getHeight(); y++) {
            for(int x = 0; x < getWidth(); x++) {
                newChunkMap[y][x] = chunkMap[y][x];
            }
        }

        this.chunkMap = newChunkMap;
    }

    public synchronized void growWest() {
        Chunk[][] newChunkMap = new Chunk[getHeight()][getWidth() + growSize];

        for(int y = 0; y < getHeight(); y++) {
            for(int x = 0; x < getWidth(); x++) {
                newChunkMap[y][x + growSize] = chunkMap[y][x];
            }
        }

        this.chunkMap = newChunkMap;
        zeroX += growSize;
    }

    public int getWidth() {
        return chunkMap[0].length;
    }

    public int getHeight() {
        return chunkMap.length;
    }

    public int getZeroX() {
        return zeroX;
    }

    public int getZeroY() {
        return zeroY;
    }

    private int getChunkMapX(int x) {
        return x + zeroX;
    }

    private int getChunkMapY(int y) {
        return y + zeroY;
    }

}
