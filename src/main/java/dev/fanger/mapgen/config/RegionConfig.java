package dev.fanger.mapgen.config;

import org.json.JSONObject;

import java.awt.Color;
import java.util.Map;

public class RegionConfig {

    private static final String JSON_ID = "id";
    private static final String JSON_NAME = "name";

    private static final String JSON_KEY_WATER_TILE = "waterTile";
    private static final String JSON_KEY_SHORE_TILE = "shoreTile";
    private static final String JSON_KEY_LAND_TILE = "landTile";

    private static final String JSON_KEY_MAX_HEIGHT = "maxHeight";
    private static final String JSON_KEY_MIN_HEIGHT = "minHeight";

    private static final String JSON_KEY_RESOURCES = "resources";
    private static final String JSON_KEY_GROUP_RESOURCES = "groupResources";
    private static final String JSON_CENTER_SPAWN_DISTANCE = "spawnDistance";
    private static final String JSON_COLOR = "color";

    private int id;
    private String name;
    private TileConfig waterTile;
    private TileConfig shoreTile;
    private TileConfig landTile;
    private double maxHeight;
    private double minHeight;
    //TODO add other stuff for resources and grouping resources
    private double centerSpawnDistance;
    private Color regionColor;

    public RegionConfig(JSONObject jsonObject, Map<Integer, TileConfig> tileConfigMap) {
        id = jsonObject.getInt(JSON_ID);
        name = jsonObject.getString(JSON_NAME);

        waterTile = tileConfigMap.get(jsonObject.getInt(JSON_KEY_WATER_TILE));
        shoreTile = tileConfigMap.get(jsonObject.getInt(JSON_KEY_SHORE_TILE));
        landTile = tileConfigMap.get(jsonObject.getInt(JSON_KEY_LAND_TILE));

        maxHeight = jsonObject.getDouble(JSON_KEY_MAX_HEIGHT);
        minHeight = jsonObject.getDouble(JSON_KEY_MIN_HEIGHT);

        centerSpawnDistance = jsonObject.optDouble(JSON_CENTER_SPAWN_DISTANCE, 0);
        regionColor = Color.decode(jsonObject.optString(JSON_COLOR, "#FFFFFF"));
    }

    public TileConfig getTileConfigForHeight(double height, double waterLevel, double shoreLevel) {
        if(height <= waterLevel) {
            return waterTile;
        } else if(height <= shoreLevel) {
            return shoreTile;
        } else {
            return landTile;
        }
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

    public double getMaxHeight() {
        return maxHeight;
    }

    public double getMinHeight() {
        return minHeight;
    }
}
