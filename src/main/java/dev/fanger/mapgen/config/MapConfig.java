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
    private static final String JSON_KEY_GROUP_REGIONS = "groupRegions";
    private static final String JSON_KEY_SPAWN_HEIGHT = "spawnHeight";

    private double spawnHeight;

    private List<TerrainConfig> heightOrderedTerrainConfigList;
    private LinkedHashMap<Integer, TerrainConfig> regionConfigMap;
    private LinkedHashMap<Integer, TileConfig> tileConfigMap;
    //TODO add resource map and resources -- also enum for resource type similar to physical properties

    public MapConfig(JSONObject jsonObject) {
        heightOrderedTerrainConfigList = new ArrayList<>();
        regionConfigMap = new LinkedHashMap<>();
        tileConfigMap = new LinkedHashMap<>();

        JSONArray allTileConfigs = jsonObject.getJSONArray(JSON_KEY_TILES);
        for(int i = 0; i < allTileConfigs.length(); i++) {
            JSONObject tileConfigJSONObject = allTileConfigs.getJSONObject(i);
            TileConfig tileConfig = new TileConfig(tileConfigJSONObject);
            tileConfigMap.put(tileConfig.getId(), tileConfig);
        }

        JSONArray allRegionConfigs = jsonObject.getJSONArray(JSON_KEY_TERRAIN);
        for(int i = 0; i < allRegionConfigs.length(); i++) {
            JSONObject regionConfigJSONObject = allRegionConfigs.getJSONObject(i);
            TerrainConfig terrainConfig = new TerrainConfig(regionConfigJSONObject, tileConfigMap);
            heightOrderedTerrainConfigList.add(terrainConfig);
            regionConfigMap.put(terrainConfig.getId(), terrainConfig);
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

    public TerrainConfig getRegionConfig(int id) {
        return regionConfigMap.get(id);
    }

    public TileConfig getTileConfig(int id) {
        return tileConfigMap.get(id);
    }

    public Collection<TerrainConfig> getRegionConfigs() {
        return regionConfigMap.values();
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
                ", regionConfigMap=" + regionConfigMap +
                ", tileConfigMap=" + tileConfigMap +
                '}';
    }
}
