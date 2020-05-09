package dev.fanger.mapgen.config;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MapConfig {

    public static final String JSON_KEY_TILES = "tiles";
    public static final String JSON_KEY_REGIONS = "regions";

    private Map<Integer, RegionConfig> regionConfigMap;
    private Map<Integer, TileConfig> tileConfigMap;

    public MapConfig(JSONObject jsonObject) {
        regionConfigMap = new HashMap<>();
        tileConfigMap = new HashMap<>();

        JSONArray allTileConfigs = jsonObject.getJSONArray(JSON_KEY_TILES);
        for(int i = 0; i < allTileConfigs.length(); i++) {
            JSONObject tileConfigJSONObject = allTileConfigs.getJSONObject(i);
            TileConfig tileConfig = new TileConfig(tileConfigJSONObject);
            tileConfigMap.put(tileConfig.getId(), tileConfig);
        }

        JSONArray allRegionConfigs = jsonObject.getJSONArray(JSON_KEY_REGIONS);
        for(int i = 0; i < allRegionConfigs.length(); i++) {
            JSONObject regionConfigJSONObject = allRegionConfigs.getJSONObject(i);
            RegionConfig regionConfig = new RegionConfig(regionConfigJSONObject, tileConfigMap);
            regionConfigMap.put(regionConfig.getId(), regionConfig);
        }
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

    @Override
    public String toString() {
        return "MapConfig{" +
                "regionConfigMap=" + regionConfigMap +
                ", tileConfigMap=" + tileConfigMap +
                '}';
    }

}
