package dev.fanger.mapgen.config;

import dev.fanger.mapgen.types.TilePhysicalProperty;
import org.json.JSONObject;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class TileConfig {

    private static final String JSON_KEY_ID = "id";
    private static final String JSON_KEY_NAME = "name";
    private static final String JSON_KEY_PHYSICAL_PROPERTY = "physical";
    private static final String JSON_KEY_COLOR = "color";
    private static final String JSON_KEY_TEXTURE_IMAGE_LOCATION = "image";

    private int id;
    private String name;
    private TilePhysicalProperty tilePhysicalProperty;
    private Color tileColor;
    private String textureImageLocation;
    private BufferedImage image;

    public TileConfig(JSONObject jsonObject) {
        id = jsonObject.getInt(JSON_KEY_ID);
        name = jsonObject.getString(JSON_KEY_NAME);
        tilePhysicalProperty = TilePhysicalProperty.valueOf(jsonObject.getString(JSON_KEY_PHYSICAL_PROPERTY));
        tileColor = Color.decode(jsonObject.optString(JSON_KEY_COLOR, "#FFFFFF"));
        textureImageLocation = jsonObject.getString(JSON_KEY_TEXTURE_IMAGE_LOCATION);
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

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
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
