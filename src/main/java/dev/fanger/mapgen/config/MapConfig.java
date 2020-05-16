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

    private List<RegionConfig> heightOrderedRegionConfigList;
    private LinkedHashMap<Integer, RegionConfig> regionConfigMap;
    private LinkedHashMap<Integer, TileConfig> tileConfigMap;
    //TODO add resource map and resources -- also enum for resource type similar to physical properties

    public MapConfig(JSONObject jsonObject) {
        heightOrderedRegionConfigList = new ArrayList<>();
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
            RegionConfig regionConfig = new RegionConfig(regionConfigJSONObject, tileConfigMap);
            heightOrderedRegionConfigList.add(regionConfig);
            regionConfigMap.put(regionConfig.getId(), regionConfig);
        }

        heightOrderedRegionConfigList.sort((o1, o2) -> {
            // Values are reversed to sort from largest to smallest
            if(o1.getSpawnHeight() < o2.getSpawnHeight()) {
                return 1;
            } else {
                return -1;
            }
        });

        spawnHeight = jsonObject.getDouble(JSON_KEY_SPAWN_HEIGHT);
    }

    public RegionConfig getRegionConfigForTile(double height) {
        for(RegionConfig regionConfig : heightOrderedRegionConfigList) {
            if(height >= regionConfig.getSpawnHeight()) {
                return regionConfig;
            }
        }

        return null;
    }

    public RegionConfig getRegionConfig(int id) {
        return regionConfigMap.get(id);
    }

    public TileConfig getTileConfig(int id) {
        return tileConfigMap.get(id);
    }

    public Collection<RegionConfig> getRegionConfigs() {
        return regionConfigMap.values();
    }

    public Collection<TileConfig> getTileConfigs() {
        return tileConfigMap.values();
    }

    public double getSpawnHeight() {
        return spawnHeight;
    }

    public List<RegionConfig> getHeightOrderedRegionConfigList() {
        return heightOrderedRegionConfigList;
    }

    @Override
    public String toString() {
        return "MapConfig{" +
                "heightOrderedRegionConfigList=" + heightOrderedRegionConfigList +
                ", regionConfigMap=" + regionConfigMap +
                ", tileConfigMap=" + tileConfigMap +
                '}';
    }
}
