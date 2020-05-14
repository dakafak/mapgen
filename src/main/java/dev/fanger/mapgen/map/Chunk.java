package dev.fanger.mapgen.map;

import dev.fanger.mapgen.config.RegionConfig;
import dev.fanger.mapgen.config.TileConfig;
import dev.fanger.mapgen.util.SeedGen;

public class Chunk {

    private int chunkX;
    private int chunkY;
    private int size;
    private int blendSize;
    private RegionConfig regionConfig;
    private Tile[][] tileGrid;

    public Chunk(int chunkX, int chunkY, int size, RegionConfig regionConfig) {
        this.chunkX = chunkX;
        this.chunkY = chunkY;
        this.size = size;
        this.blendSize = 2;
        this.regionConfig = regionConfig;
        tileGrid = new Tile[size][size];
    }

    public double getQ1Height() {
        return tileGrid[0][size - 1].getHeight();
    }

    public double getQ2Height() {
        return tileGrid[0][0].getHeight();
    }

    public double getQ3Height() {
        return tileGrid[size - 1][0].getHeight();
    }

    public double getQ4Height() {
        return tileGrid[size - 1][size - 1].getHeight();
    }

    public Tile getTile(double gameX, double gameY) {
        int tileX = ((int) gameX) - (chunkX * size);
        int tileY = ((int) gameY) - (chunkY * size);
        return tileGrid[tileY][tileX];
    }

    public void generate(double[][] tileHeightMaps, short seed, double waterLevel, double shoreLevel, Chunk northChunk, Chunk southChunk, Chunk eastChunk, Chunk westChunk) {
        for(int y = 0; y < tileGrid.length; y++) {
            for(int x = 0; x < tileGrid[0].length; x++) {
                int tileX = (chunkX * size) + x;
                int tileY = (chunkY * size) + y;
                double height = tileHeightMaps[y][x];

                RegionConfig regionConfigForTile = getBlendedRegionConfigForTile(
                        x,
                        y,
                        seed,
                        northChunk,
                        southChunk,
                        eastChunk,
                        westChunk);
                TileConfig tileConfig = regionConfigForTile.getTileConfigForHeight(height, waterLevel, shoreLevel);

                // Place new tile with region config, blending or current chunk
                Tile tile = new Tile(tileX, tileY, height, tileConfig, this);
                tileGrid[y][x] = tile;
            }
        }
    }

    private RegionConfig getBlendedRegionConfigForTile(int gridX, int gridY, short seed, Chunk northChunk, Chunk southChunk, Chunk eastChunk, Chunk westChunk) {
        boolean shouldBlend = SeedGen.randomNumber(gridX, gridY, seed, 100) <= 50;

        if(shouldBlend) {
            // North chunk blending
            if(northChunk != null && gridY < blendSize) {
                return northChunk.getRegionConfig();
            } else if(southChunk != null && gridY >= (size - blendSize)) {
                return southChunk.getRegionConfig();
            } else if(eastChunk != null && gridX >= (size - blendSize)) {
                return eastChunk.getRegionConfig();
            } else if(westChunk != null && gridX < blendSize) {
                return westChunk.getRegionConfig();
            }
        }

        return regionConfig;
    }

    public Tile[][] getTileGrid() {
        return tileGrid;
    }

    public RegionConfig getRegionConfig() {
        return regionConfig;
    }
}
