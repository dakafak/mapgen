package dev.fanger.mapgen.map;

import dev.fanger.mapgen.config.MapConfig;
import dev.fanger.mapgen.config.ResourceConfig;
import dev.fanger.mapgen.config.TerrainConfig;
import dev.fanger.mapgen.config.TileConfig;
import dev.fanger.mapgen.util.SeedGen;

public class Chunk {

    private int chunkX;
    private int chunkY;
    private int size;
    private Tile[][] tileGrid;

    public Chunk(int chunkX, int chunkY, int size) {
        this.chunkX = chunkX;
        this.chunkY = chunkY;
        this.size = size;
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
        int tileX = ((int) Math.floor(gameX)) - (chunkX * size);
        int tileY = ((int) Math.floor(gameY)) - (chunkY * size);

        return tileGrid[tileY][tileX];
    }

    public void generate(double[][] tileHeightMaps, long seed, MapConfig mapConfig) {
        for(int y = 0; y < tileGrid.length; y++) {
            for(int x = 0; x < tileGrid[0].length; x++) {
                int tileX = (chunkX * size) + x;
                int tileY = (chunkY * size) + y;
                double height = tileHeightMaps[y][x];

                TerrainConfig terrainConfigForTile = mapConfig.getRegionConfigForTile(height);
                TileConfig tileConfig = terrainConfigForTile.getTileConfig();

                // Generate resource
                ResourceConfig resourceConfig = null;

                double currentTotalChance = 0;
                double spawnChance = SeedGen.randomNumberRefreshSeed(x, y, seed, 1);

                for(int i = 0; i < terrainConfigForTile.getResourceConfigs().length; i++) {
                    ResourceConfig config = terrainConfigForTile.getResourceConfigs()[i];
                    double chance = terrainConfigForTile.getSpawnChances()[i];

                    if(spawnChance <= chance + currentTotalChance) {
                        resourceConfig = config;
                        break;
                    }

                    currentTotalChance += chance;
                }

                // Place new tile with region config
                Tile tile = new Tile(tileX, tileY, height, tileConfig, terrainConfigForTile, resourceConfig, this);
                tileGrid[y][x] = tile;
            }
        }
    }

    public int getChunkX() {
        return chunkX;
    }

    public int getChunkY() {
        return chunkY;
    }

    public Tile[][] getTileGrid() {
        return tileGrid;
    }

}
