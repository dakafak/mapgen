package dev.fanger.mapgen.config;

import dev.fanger.mapgen.types.TilePhysicalProperty;
import org.json.JSONObject;

import java.awt.Color;

public class TileConfig {

    private static final String JSON_ID = "id";
    private static final String JSON_NAME = "name";
    private static final String JSON_PHYSICAL_PROPERTY = "physical";
    private static final String JSON_COLOR = "color";
    private static final String JSON_TEXTURE_IMAGE_LOCATION = "image";

    private int id;
    private String name;
    private TilePhysicalProperty tilePhysicalProperty;
    private Color tileColor;
    private String textureImageLocation;

    public TileConfig(JSONObject jsonObject) {
        id = jsonObject.getInt(JSON_ID);
        name = jsonObject.getString(JSON_NAME);
        tilePhysicalProperty = TilePhysicalProperty.valueOf(jsonObject.getString(JSON_PHYSICAL_PROPERTY));
        tileColor = Color.decode(jsonObject.getString(JSON_COLOR));
        textureImageLocation = jsonObject.getString(JSON_TEXTURE_IMAGE_LOCATION);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TilePhysicalProperty getTilePhysicalProperty() {
        return tilePhysicalProperty;
    }

    public Color getTileColor() {
        return tileColor;
    }

    public String getTextureImageLocation() {
        return textureImageLocation;
    }

    @Override
    public String toString() {
        return "TileConfig{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tilePhysicalProperty=" + tilePhysicalProperty +
                ", tileColor=" + tileColor +
                ", textureImageLocation='" + textureImageLocation + '\'' +
                '}';
    }

}
