package dev.fanger.mapgen.config;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.Color;
import java.util.Map;

public class TerrainConfig {

    private static final String JSON_NAME = "name";
    private static final String JSON_KEY_TILE = "tile";
    private static final String JSON_KEY_SPAWN_HEIGHT = "spawnHeight";
    private static final String JSON_KEY_RESOURCES = "resources";
    private static final String JSON_COLOR = "color";

    private String name;
    private TileConfig tileConfig;
    private double spawnHeight;
    private Color regionColor;
    private ResourceConfig[] resourceConfigs;
    private double[] spawnChances;

    public TerrainConfig(JSONObject jsonObject, Map<Integer, TileConfig> tileConfigMap, Map<String, ResourceConfig> resourceConfigMap) {
        name = jsonObject.getString(JSON_NAME);
        tileConfig = tileConfigMap.get(jsonObject.getInt(JSON_KEY_TILE));
        spawnHeight = jsonObject.getDouble(JSON_KEY_SPAWN_HEIGHT);
        regionColor = Color.decode(jsonObject.optString(JSON_COLOR, "#FFFFFF"));

        JSONArray resourceIdArray = jsonObject.getJSONArray(JSON_KEY_RESOURCES);
        resourceConfigs = new ResourceConfig[resourceIdArray.length()];
        spawnChances = new double[resourceIdArray.length()];
        for(int i = 0; i < resourceIdArray.length(); i++) {
            JSONObject resourceObject = resourceIdArray.getJSONObject(i);
            String resourceType = resourceObject.getString("type");
            double spawnChance = resourceObject.getDouble("chance");

            ResourceConfig resourceConfig = resourceConfigMap.get(resourceType);
            resourceConfigs[i] = resourceConfig;
            spawnChances[i] = spawnChance;
        }
    }

    public String getName() {
        return name;
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

    public ResourceConfig[] getResourceConfigs() {
        return resourceConfigs;
    }

    public double[] getSpawnChances() {
        return spawnChances;
    }
}
