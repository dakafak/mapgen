package dev.fanger.mapgen.config;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

public class MapConfig {

    public static final String JSON_KEY_TILES = "tiles";
    public static final String JSON_KEY_REGIONS = "regions";

    private List<RegionConfig> regionConfigList;
    private LinkedHashMap<Integer, RegionConfig> regionConfigMap;
    private LinkedHashMap<Integer, TileConfig> tileConfigMap;

    public MapConfig(JSONObject jsonObject) {
        regionConfigList = new ArrayList<>();
        regionConfigMap = new LinkedHashMap<>();
        tileConfigMap = new LinkedHashMap<>();

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
            regionConfigList.add(regionConfig);
            regionConfigMap.put(regionConfig.getId(), regionConfig);
        }
    }

    public RegionConfig getRandomRegionConfig() {
        int randomConfigIndex = (int) Math.floor(Math.random() * regionConfigList.size());
        return regionConfigList.get(randomConfigIndex);
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
