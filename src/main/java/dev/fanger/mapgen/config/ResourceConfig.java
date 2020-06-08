package dev.fanger.mapgen.config;

import org.json.JSONObject;

import java.awt.Color;

public class ResourceConfig {

    private static final String JSON_KEY_RESOURCE_TYPE = "type";
    private static final String JSON_KEY_SIZE = "size";
    private static final String JSON_KEY_HEALTH = "health";
    private static final String JSON_KEY_COLOR = "color";
    private static final String JSON_KEY_TEXTURE_IMAGE_LOCATION = "image";

    //TODO add a health value to the resource config
    //TODO add sparsity to resource config (grouping would be nice...)

    private String resourceType;
    private double size;
    private double health;
    private Color resourceColor;
    private String textureImageLocation;

    public ResourceConfig(JSONObject jsonObject) {
        resourceType = jsonObject.getString(JSON_KEY_RESOURCE_TYPE);
        size = jsonObject.getDouble(JSON_KEY_SIZE);
        health = jsonObject.getDouble(JSON_KEY_HEALTH);
        resourceColor = Color.decode(jsonObject.optString(JSON_KEY_COLOR, "#FFFFFF"));
        textureImageLocation = jsonObject.getString(JSON_KEY_TEXTURE_IMAGE_LOCATION);
    }

    public String getResourceType() {
        return resourceType;
    }

    public Color getResourceColor() {
        return resourceColor;
    }

    public String getTextureImageLocation() {
        return textureImageLocation;
    }

    public double getSize() {
        return size;
    }

    public double getHealth() {
        return health;
    }
}
