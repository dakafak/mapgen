package dev.fanger.mapgen.config;

import org.json.JSONObject;

import java.awt.Color;
import java.util.Map;

public class RegionConfig {

    private static final String JSON_ID = "id";
    private static final String JSON_NAME = "name";
    private static final String JSON_KEY_TILE = "tile";
    private static final String JSON_KEY_SPAWN_HEIGHT = "spawnHeight";

    private static final String JSON_KEY_RESOURCES = "resources";
    private static final String JSON_KEY_GROUP_RESOURCES = "groupResources";
    private static final String JSON_CENTER_SPAWN_DISTANCE = "spawnDistance";
    private static final String JSON_COLOR = "color";

    private int id;
    private String name;
    private TileConfig tileConfig;
    private double spawnHeight;
    //TODO add other stuff for resources and grouping resources
    private double centerSpawnDistance;
    private Color regionColor;

    public RegionConfig(JSONObject jsonObject, Map<Integer, TileConfig> tileConfigMap) {
        id = jsonObject.getInt(JSON_ID);
        name = jsonObject.getString(JSON_NAME);

        tileConfig = tileConfigMap.get(jsonObject.getInt(JSON_KEY_TILE));

        spawnHeight = jsonObject.getDouble(JSON_KEY_SPAWN_HEIGHT);

        centerSpawnDistance = jsonObject.optDouble(JSON_CENTER_SPAWN_DISTANCE, 0);
        regionColor = Color.decode(jsonObject.optString(JSON_COLOR, "#FFFFFF"));
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getCenterSpawnDistance() {
        return centerSpawnDistance;
    }

    public Color getRegionColor() {
        return regionColor;
    }

    public double getSpawnHeight() {
        return spawnHeight;
    }

    public TileConfig getTileConfig() {
        return tileConfig;
    }
}
