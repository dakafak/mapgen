package dev.fanger.mapgen.map;

import dev.fanger.mapgen.config.RegionConfig;
import dev.fanger.mapgen.config.TileConfig;

public class Chunk {

    private int chunkX;
    private int chunkY;
    private int size;
    private RegionConfig regionConfig;
    private Tile[][] tileGrid;

    public Chunk(int chunkX, int chunkY, int size, RegionConfig regionConfig) {
        this.chunkX = chunkX;
        this.chunkY = chunkY;
        this.size = size;
        this.regionConfig = regionConfig;
        tileGrid = new Tile[size][size];
    }

    public Tile getTile(double gameX, double gameY) {
        int tileX = ((int) gameX) - (chunkX * size);
        int tileY = ((int) gameY) - (chunkY * size);
        return tileGrid[tileY][tileX];
    }

    public void generate(double[][] tileHeightMaps) {
        for(int y = 0; y < tileGrid.length; y++) {
            for(int x = 0; x < tileGrid[0].length; x++) {
                int tileX = (chunkX * size) + x;
                int tileY = (chunkY * size) + y;
                double height = tileHeightMaps[y][x];
                TileConfig tileConfig = regionConfig.getTileConfigFrom100Range(height);
                Tile tile = new Tile(tileX, tileY, height, tileConfig, this);
                tileGrid[y][x] = tile;
            }
        }
    }

    public Tile[][] getTileGrid() {
        return tileGrid;
    }

}
