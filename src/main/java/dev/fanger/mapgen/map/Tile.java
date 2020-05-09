package dev.fanger.mapgen.map;

import dev.fanger.mapgen.config.TileConfig;

public class Tile {

    private int x;// Be sure to round coordinate values to find tile coordinates and the x and y should be the center of the tile and units and objects
    private int y;
    private TileConfig tileConfig;
    private Chunk parentChunk;// An easy way to determine what chunk a tile is part of, for region properties

}
