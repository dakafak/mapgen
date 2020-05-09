package dev.fanger.mapgen;

import dev.fanger.mapgen.config.MapConfig;
import dev.fanger.mapgen.util.ConfigLoader;
import org.json.JSONObject;

import java.io.IOException;

public class TestMapGen {

    public static void main(String[] args) {
        try {
            JSONObject loadedJsonConfig = ConfigLoader.jsonFromResource("exampleConfig.json");
            System.out.println(loadedJsonConfig);

            MapConfig mapConfig = new MapConfig(loadedJsonConfig);
            System.out.println(mapConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
