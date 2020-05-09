package dev.fanger.mapgen.config;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

public class RegionConfig {

    private static final String JSON_ID = "id";
    private static final String JSON_NAME = "name";
    private static final String JSON_TILE_IDS = "tiles";
    private static final String JSON_TILE_WEIGHT_PREFIX = "x";

    private int id;
    private String name;
    private LinkedHashMap<TileConfig, Double> tileConfigToWeight;
    private LinkedHashMap<TileConfig, Double> tileConfigToWeightRange;
    private double totalTileWeight;

    public RegionConfig(JSONObject jsonObject, Map<Integer, TileConfig> tileConfigMap) {
        id = jsonObject.getInt(JSON_ID);
        name = jsonObject.getString(JSON_NAME);
        tileConfigToWeight = new LinkedHashMap<>();
        tileConfigToWeightRange = new LinkedHashMap<>();

        JSONArray jsonArray = jsonObject.getJSONArray(JSON_TILE_IDS);
        for(int i = 0; i < jsonArray.length(); i++) {
            String tileAndWeight = jsonArray.getString(i);
            String[] splitTileAndWeight = tileAndWeight.split(JSON_TILE_WEIGHT_PREFIX);

            int tileId = 0;
            double weight = 1;
            if(splitTileAndWeight.length == 1) {
                tileId = Integer.valueOf(splitTileAndWeight[0]);
            } else if(splitTileAndWeight.length == 2) {
                tileId = Integer.valueOf(splitTileAndWeight[0]);
                weight = Double.valueOf(splitTileAndWeight[1]);
            }//TODO change config to have objects and numbers

            tileConfigToWeight.put(tileConfigMap.get(tileId), weight);
            totalTileWeight += weight;
        }

        double totalWeightRangeSoFar = 0;
        for(TileConfig tileConfig : tileConfigToWeight.keySet()) {
            double weightProportion = (tileConfigToWeight.get(tileConfig) / totalTileWeight) * 100;
            totalWeightRangeSoFar += weightProportion;
            if(totalWeightRangeSoFar > 100) {
                totalWeightRangeSoFar = 100;
            }
            tileConfigToWeightRange.put(tileConfig, totalWeightRangeSoFar);
        }
    }

    public TileConfig getTileConfigFrom100Range(double range) {
        for(TileConfig tileConfig : tileConfigToWeightRange.keySet()) {
            if(range <= tileConfigToWeightRange.get(tileConfig)) {
                return tileConfig;
            }
        }

        // This should never return null if proper range numbers are used
        return null;
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
