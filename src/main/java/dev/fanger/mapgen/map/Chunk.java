package dev.fanger.mapgen.map;

import dev.fanger.mapgen.config.RegionConfig;

import java.util.List;

public class Chunk {

    private RegionConfig regionConfig;
    private Tile[][] tileGrid;

    //TODO maybe turn this into a map or something
    // -- or add a map that maps this object to the nearest coordinate for faster lookup
    private List<MapObject> mapObjectList;

}
