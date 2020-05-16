package dev.fanger.mapgen.config;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

public class MapConfig {

    private static final String JSON_KEY_TILES = "tiles";
    private static final String JSON_KEY_TERRAIN = "terrain";
    private static final String JSON_KEY_RESOURCES = "resources";
    private static final String JSON_KEY_SPAWN_HEIGHT = "spawnHeight";

    private double spawnHeight;

    private LinkedHashMap<Integer, TileConfig> tileConfigMap;
    private LinkedHashMap<Integer, ResourceConfig> resourceConfigMap;
    private List<TerrainConfig> heightOrderedTerrainConfigList;

    public MapConfig(JSONObject jsonObject) {
        tileConfigMap = new LinkedHashMap<>();
        resourceConfigMap = new LinkedHashMap<>();
        heightOrderedTerrainConfigList = new ArrayList<>();

        JSONArray allTileConfigs = jsonObject.getJSONArray(JSON_KEY_TILES);
        for(int i = 0; i < allTileConfigs.length(); i++) {
            JSONObject tileConfigJSONObject = allTileConfigs.getJSONObject(i);
            TileConfig tileConfig = new TileConfig(tileConfigJSONObject);
            tileConfigMap.put(tileConfig.getId(), tileConfig);
        }

        JSONArray allResources = jsonObject.getJSONArray(JSON_KEY_RESOURCES);
        for(int i = 0; i < allResources.length(); i++) {
            JSONObject resourceJSONObject = allResources.getJSONObject(i);
            ResourceConfig resourceConfig = new ResourceConfig(resourceJSONObject);
            resourceConfigMap.put(resourceConfig.getId(), resourceConfig);
        }

        JSONArray allRegionConfigs = jsonObject.getJSONArray(JSON_KEY_TERRAIN);
        for(int i = 0; i < allRegionConfigs.length(); i++) {
            JSONObject regionConfigJSONObject = allRegionConfigs.getJSONObject(i);
            TerrainConfig terrainConfig = new TerrainConfig(regionConfigJSONObject, tileConfigMap, resourceConfigMap);
            heightOrderedTerrainConfigList.add(terrainConfig);
        }

        heightOrderedTerrainConfigList.sort((o1, o2) -> {
            // Values are reversed to sort from largest to smallest
            if(o1.getSpawnHeight() < o2.getSpawnHeight()) {
                return 1;
            } else {
                return -1;
            }
        });

        spawnHeight = jsonObject.getDouble(JSON_KEY_SPAWN_HEIGHT);
    }

    public TerrainConfig getRegionConfigForTile(double height) {
        for(TerrainConfig terrainConfig : heightOrderedTerrainConfigList) {
            if(height >= terrainConfig.getSpawnHeight()) {
                return terrainConfig;
            }
        }

        return null;
    }

    public TileConfig getTileConfig(int id) {
        return tileConfigMap.get(id);
    }

    public Collection<TileConfig> getTileConfigs() {
        return tileConfigMap.values();
    }

    public double getSpawnHeight() {
        return spawnHeight;
    }

    public List<TerrainConfig> getHeightOrderedTerrainConfigList() {
        return heightOrderedTerrainConfigList;
    }

    @Override
    public String toString() {
        return "MapConfig{" +
                "heightOrderedTerrainConfigList=" + heightOrderedTerrainConfigList +
                ", tileConfigMap=" + tileConfigMap +
                '}';
    }
}
