package dev.fanger.mapgen.config;

import dev.fanger.mapgen.util.ConfigLoader;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class RegionConfigTest {

    @Test
    public void testGetTileConfigFrom100Range() throws IOException {
        JSONObject loadedJsonConfig = ConfigLoader.jsonFromResource("exampleConfig.json");
        MapConfig mapConfig = new MapConfig(loadedJsonConfig);

        assertNotNull(mapConfig.getRegionConfig(1).getTileConfigFrom100Range(0));
        assertNotNull(mapConfig.getRegionConfig(1).getTileConfigFrom100Range(99.99));
        assertNull(mapConfig.getRegionConfig(1).getTileConfigFrom100Range(100.01));
    }

}
