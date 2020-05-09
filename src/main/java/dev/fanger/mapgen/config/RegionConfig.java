package dev.fanger.mapgen.config;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegionConfig {

    private static final String JSON_ID = "id";
    private static final String JSON_NAME = "name";
    private static final String JSON_TILE_IDS = "tiles";
    private static final String JSON_TILE_WEIGHT_PREFIX = "x";

    private int id;
    private String name;
    private Map<TileConfig, Double> tileConfigToWeight;
    private double totalTileWeight;

    public RegionConfig(JSONObject jsonObject, Map<Integer, TileConfig> tileConfigMap) {
        id = jsonObject.getInt(JSON_ID);
        name = jsonObject.getString(JSON_NAME);
        tileConfigToWeight = new HashMap<>();

        JSONArray jsonArray = jsonObject.getJSONArray(JSON_TILE_IDS);
        for(int i = 0; i < jsonArray.length(); i++) {
            String tileAndWeight = jsonArray.getString(i);
            String[] splitTileAndWeight = tileAndWeight.split(JSON_TILE_WEIGHT_PREFIX);

            int tileId = 0;
            double weight = 0;
            if(splitTileAndWeight.length == 1) {
                tileId = Integer.valueOf(splitTileAndWeight[0]);
                weight = 1;
            } else if(splitTileAndWeight.length == 2) {
                tileId = Integer.valueOf(splitTileAndWeight[0]);
                weight = Double.valueOf(splitTileAndWeight[1]);
            }

            tileConfigToWeight.put(tileConfigMap.get(tileId), weight);
            totalTileWeight += weight;
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<TileConfig, Double> getTileConfigToWeight() {
        return tileConfigToWeight;
    }

    public double getTotalTileWeight() {
        return totalTileWeight;
    }

    @Override
    public String toString() {
        return "RegionConfig{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tileConfigToWeight=" + tileConfigToWeight +
                ", totalTileWeight=" + totalTileWeight +
                '}';
    }

}
