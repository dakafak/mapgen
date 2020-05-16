package dev.fanger.mapgen.map;

import dev.fanger.mapgen.config.RegionConfig;
import dev.fanger.mapgen.config.TileConfig;

public class Tile {

    private int x;// Be sure to round coordinate values to find tile coordinates and the x and y should be the center of the tile and units and objects
    private int y;
    private double height;// Used to determine what tile to use to randomize terrain - primarily water/shore/land -- probably don't actually need to save this here...
    private TileConfig tileConfig;
    private RegionConfig regionConfig;
    private Chunk parentChunk;// An easy way to determine what chunk a tile is part of, for region properties

    public Tile(int x, int y, double height, TileConfig tileConfig, RegionConfig regionConfig, Chunk parentChunk) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.tileConfig = tileConfig;
        this.regionConfig = regionConfig;
        this.parentChunk = parentChunk;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getHeight() {
        return height;
    }

    public TileConfig getTileConfig() {
        return tileConfig;
    }

    public Chunk getParentChunk() {
        return parentChunk;
    }
}
