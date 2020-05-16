package dev.fanger.mapgen.map;

import dev.fanger.mapgen.config.ResourceConfig;
import dev.fanger.mapgen.types.ResourceType;

public class Resource {

    private int x;
    private int y;
    private Tile tile;
    private ResourceConfig resourceConfig;
    private ResourceType resourceType;

    public Resource(int x, int y, Tile tile, ResourceConfig resourceConfig, ResourceType resourceType) {
        this.x = x;
        this.y = y;
        this.tile = tile;
        this.resourceConfig = resourceConfig;
        this.resourceType = resourceType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Tile getTile() {
        return tile;
    }

    public ResourceConfig getResourceConfig() {
        return resourceConfig;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }
}
