package dev.fanger.mapgen.config;

import dev.fanger.mapgen.types.ResourceType;
import org.json.JSONObject;

import java.awt.Color;

public class ResourceConfig {

    private static final String JSON_KEY_ID = "id";
    private static final String JSON_KEY_NAME = "name";
    private static final String JSON_KEY_RESOURCE_TYPE = "type";
    private static final String JSON_KEY_COLOR = "color";
    private static final String JSON_KEY_TEXTURE_IMAGE_LOCATION = "image";

    //TODO add a health value to the resource config
    //TODO add sparsity to resource config (grouping would be nice...)

    private int id;
    private String name;
    private ResourceType resourceType;
    private Color resourceColor;
    private String textureImageLocation;

    public ResourceConfig(JSONObject jsonObject) {
        id = jsonObject.getInt(JSON_KEY_ID);
        name = jsonObject.getString(JSON_KEY_NAME);
        resourceType = ResourceType.valueOf(jsonObject.getString(JSON_KEY_RESOURCE_TYPE));
        resourceColor = Color.decode(jsonObject.optString(JSON_KEY_COLOR, "#FFFFFF"));
        textureImageLocation = jsonObject.getString(JSON_KEY_TEXTURE_IMAGE_LOCATION);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public Color getResourceColor() {
        return resourceColor;
    }

    public String getTextureImageLocation() {
        return textureImageLocation;
    }
}
